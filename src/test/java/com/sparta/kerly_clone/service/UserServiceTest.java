package com.sparta.kerly_clone.service;

import com.sparta.kerly_clone.dto.requestDto.SignupRequestDto;
import com.sparta.kerly_clone.exception.DuplicateUserException;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.repository.UserRepository;
import com.sparta.kerly_clone.security.JwtTokenProvider;
import com.sparta.kerly_clone.util.SignupValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @Mock
    AuthenticationManagerBuilder authenticationManagerBuilder;

    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Nested
    @DisplayName("이메일 중복체크")
    class DupCheck {
        @Test
        @DisplayName("이메일 중복없음")
        void 이메일중복없음(){
            //given
            SignupRequestDto signupRequestDto = new SignupRequestDto("user@email.com","password","user");

            PasswordEncoder passwordEncoder = passwordEncoder();
            SignupValidator signupValidator = new SignupValidator(userRepository, passwordEncoder);

            UserService userService = new UserService(
                    signupValidator,
                    userRepository,
                    jwtTokenProvider,
                    authenticationManagerBuilder,
                    passwordEncoder);

            Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                    .thenReturn(Optional.empty());
            //when
            boolean result = userService.checkDupEmail(signupRequestDto.getEmail());
            //then
            assertThat(result).isEqualTo(true);
        }

        @Test
        @DisplayName("이메일 중복 예외발생")
        void 이메일중복(){
            //given
            SignupRequestDto signupRequestDto = new SignupRequestDto("user@email.com","password","user");

            PasswordEncoder passwordEncoder = passwordEncoder();
            SignupValidator signupValidator = new SignupValidator(userRepository, passwordEncoder);

            UserService userService = new UserService(
                    signupValidator,
                    userRepository,
                    jwtTokenProvider,
                    authenticationManagerBuilder,
                    passwordEncoder);

            Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                    .thenReturn(Optional.of(new User()));
            //when
            assertThrows(DuplicateUserException.class, () -> userService.checkDupEmail(signupRequestDto.getEmail()));
            //then
        }
    }

    @Nested
    @DisplayName("회원가입")
    class SignUp {
        @Test
        @DisplayName("정상")
        void 회원가입(){
            //given
            SignupRequestDto signupRequestDto = new SignupRequestDto("user@email.com","password","user");

            PasswordEncoder passwordEncoder = passwordEncoder();
            SignupValidator signupValidator = new SignupValidator(userRepository, passwordEncoder);

            UserService userService = new UserService(
                    signupValidator,
                    userRepository,
                    jwtTokenProvider,
                    authenticationManagerBuilder,
                    passwordEncoder);

            Mockito.when(userRepository.findByEmail("user@email.com"))
                    .thenReturn(Optional.empty());
            //when
            boolean result = userService.registerUser(signupRequestDto);
            //then
            assertThat(result).isEqualTo(true);
        }

        @Test
        @DisplayName("실패 - 이메일중복")
        void 이메일중복(){
            //given

            //when

            //then
        }
    }
}