package com.sparta.kerly_clone.service;

import com.sparta.kerly_clone.dto.SignupRequestDto;
import com.sparta.kerly_clone.dto.UserRequestDto;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.repository.UserRepository;
import com.sparta.kerly_clone.security.JwtTokenProvider;
import com.sparta.kerly_clone.util.SignupValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final SignupValidator signupValidator;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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
                new UsernamePasswordAuthenticationToken(userRequestDto.getEmail(),userRequestDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authentication);
    }
}
