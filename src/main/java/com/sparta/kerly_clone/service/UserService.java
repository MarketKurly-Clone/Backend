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

    public boolean registerUser(SignupRequestDto signupRequestDto) {
        User user = signupValidator.validate(signupRequestDto);
        userRepository.save(user);
        return true;
    }

    public boolean checkDupEmail(String email) {
        return signupValidator.validate(email);
    }

    public String createToken(UserRequestDto userRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userRequestDto.getEmail(), userRequestDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authentication);
    }

    public User loginValideCheck(UserRequestDto userRequestDto) {

        String email = userRequestDto.getEmail().trim();
        String password = userRequestDto.getPassword().trim();
        if (email.equals("") || password.equals(""))
            throw new EmptyException("로그인 정보를 모두 입력해주세요.");

        User userEmail = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("회원정보가 일치하지 않습니다. 다시 입력해주세요."));

        if (!passwordEncoder.matches(password, userEmail.getPassword())) {
            throw new UsernameNotFoundException("회원정보가 일치하지 않습니다. 다시 입력해주세요.");
        }

        return userEmail;
    }

    public User loadUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("로그인 정보가 존재하지 않습니다.")
        );
    }
}
