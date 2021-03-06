package com.sparta.kerly_clone.service;

import com.sparta.kerly_clone.dto.requestDto.SignupRequestDto;
import com.sparta.kerly_clone.dto.requestDto.UserRequestDto;
import com.sparta.kerly_clone.exception.EmptyException;
import com.sparta.kerly_clone.exception.UsernameNotFoundException;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.repository.UserRepository;
import com.sparta.kerly_clone.security.JwtTokenProvider;
import com.sparta.kerly_clone.util.SignupValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final SignupValidator signupValidator;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

//    public boolean registerUser(SignupRequestDto signupRequestDto) {
//        User user = signupValidator.validate(signupRequestDto);
//        userRepository.save(user);
//        return true;
//    }

    public boolean checkDupEmail(String email) {
        return signupValidator.validate(email);
    }

    public String createToken(UserRequestDto userRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userRequestDto.getEmail(), userRequestDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authentication);
    }

    public User loginValidCheck(UserRequestDto userRequestDto) {

        String email = userRequestDto.getEmail().trim();
        String password = userRequestDto.getPassword().trim();
        if (email.equals("") || password.equals(""))
            throw new EmptyException("????????? ????????? ?????? ??????????????????.");

        User userEmail = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("??????????????? ???????????? ????????????. ?????? ??????????????????."));

        if (!passwordEncoder.matches(password, userEmail.getPassword())) {
            throw new UsernameNotFoundException("??????????????? ???????????? ????????????. ?????? ??????????????????.");
        }

        return userEmail;
    }

    public User loadUserEamil(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("????????? ????????? ???????????? ????????????.")
        );
    }

    public boolean registerUser(String email, String password, String username) {
        User user = signupValidator.validate(new SignupRequestDto(email, password, username));
        userRepository.save(user);
        return true;
    }

    public User loginValidCheck(String email, String password) {
        if (email.equals("") || password.equals(""))
            throw new EmptyException("????????? ????????? ?????? ??????????????????.");

        User userEmail = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("??????????????? ???????????? ????????????. ?????? ??????????????????."));

        if (!passwordEncoder.matches(password, userEmail.getPassword())) {
            throw new UsernameNotFoundException("??????????????? ???????????? ????????????. ?????? ??????????????????.");
        }
        return userEmail;
    }

    public String createToken(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authentication);
    }
}
