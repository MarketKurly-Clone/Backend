package com.sparta.kerly_clone.service;

import com.sparta.kerly_clone.dto.requestDto.SignupRequestDto;
import com.sparta.kerly_clone.dto.requestDto.UserRequestDto;
import com.sparta.kerly_clone.exception.*;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.repository.UserRepository;
import com.sparta.kerly_clone.security.JwtTokenProvider;
import com.sparta.kerly_clone.util.SignupValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    @Mock
    PasswordEncoder passwordEncoder;

    SignupValidator signupValidator;
    UserService userService;

    @BeforeEach
    void init() {
        signupValidator = new SignupValidator(userRepository, passwordEncoder);
        userService = new UserService(
                signupValidator,
                userRepository,
                jwtTokenProvider,
                authenticationManagerBuilder,
                passwordEncoder);
    }

    @Nested
    @DisplayName("이메일 중복체크")
    class DupCheck {
        @Test
        @DisplayName("이메일 중복없음")
        void 이메일중복없음() {
            //given
            SignupRequestDto signupRequestDto = new SignupRequestDto("user@email.com", "password", "user");

            Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                    .thenReturn(Optional.empty());
            //when
            boolean result = userService.checkDupEmail(signupRequestDto.getEmail());
            //then
            assertThat(result).isEqualTo(true);
        }

        @Test
        @DisplayName("이메일 중복 예외발생")
        void 이메일중복() {
            //given
            SignupRequestDto signupRequestDto = new SignupRequestDto("user@email.com", "password", "user");

            Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                    .thenReturn(Optional.of(new User()));
            //when
            //then
            assertThrows(DuplicateUserException.class, () -> userService.checkDupEmail(signupRequestDto.getEmail()), "이메일이 중복되었습니다.");
        }
    }

    @Nested
    @DisplayName("회원가입")
    class SignUp {
        @Test
        @DisplayName("정상")
        void 회원가입() {
            //given
            SignupRequestDto signupRequestDto = new SignupRequestDto("user@email.com", "password486", "user");

            Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                    .thenReturn(Optional.empty());
            //when
            boolean result = userService.registerUser(signupRequestDto);
            //then
            assertThat(result).isEqualTo(true);
        }

        @Nested
        @DisplayName("회원가입 실패")
        class SignUpFail {
            @Nested
            @DisplayName("이메일 중복")
            class DupError {
                @Test
                @DisplayName("이미 가입된 이메일이 있으면 중복 예외발생")
                void 이메일중복() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("user@email.com", "password486", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.of(new User()));
                    //when
                    //then
                    assertThrows(DuplicateUserException.class, () -> userService.registerUser(signupRequestDto), "이메일이 중복되었습니다.");
                }
            }

            @Nested
            @DisplayName("이메일 형식")
            class SignUpFail2 {
                @Test
                @DisplayName("@없는 이메일 형식일 경우 예외 발생")
                void 이메일형식불량1() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("usermail.com", "password486", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(EmailFormException.class, () -> userService.registerUser(signupRequestDto), "이메일 형식으로 입력해주세요.");
                }

                @Test
                @DisplayName(".없는 이메일 형식일 경우 예외 발생")
                void 이메일형식불량2() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("user@mailcom", "password486", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(EmailFormException.class, () -> userService.registerUser(signupRequestDto), "이메일 형식으로 입력해주세요.");
                }

                @Test
                @DisplayName("도메인 형태가 (.com)일 경우 예외 발생")
                void 이메일형식불량3() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("user@.com", "password486", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(EmailFormException.class, () -> userService.registerUser(signupRequestDto), "이메일 형식으로 입력해주세요.");
                }

                @Test
                @DisplayName("도메인 형태가 (mail.)일 경우 예외 발생")
                void 이메일형식불량4() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("user@mail.", "password486", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(EmailFormException.class, () -> userService.registerUser(signupRequestDto), "이메일 형식으로 입력해주세요.");
                }

                @Test
                @DisplayName("도메인 형태가 (.)일 경우 예외 발생")
                void 이메일형식불량5() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("user@.", "password486", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(EmailFormException.class, () -> userService.registerUser(signupRequestDto), "이메일 형식으로 입력해주세요.");
                }

                @Test
                @DisplayName("아이디가 없을 경우 예외 발생")
                void 이메일형식불량6() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("@mail.com", "password486", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(EmailFormException.class, () -> userService.registerUser(signupRequestDto), "이메일 형식으로 입력해주세요.");
                }
            }

            @Nested
            @DisplayName("비밀번호")
            class PasswordError {
                @Test
                @DisplayName("비밀번호 길이가 짧을 시 예외발생")
                void 비밀번호길이불량1() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("user@mail.com", "pass486", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(LengthException.class, () -> userService.registerUser(signupRequestDto), "비밀번호 8~16자리의 영문과 숫자를 조합해주세요.");
                }

                @Test
                @DisplayName("비밀번호 길이 초과 시 예외발생")
                void 비밀번호길이불량2() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("user@mail.com", "password123456789", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(LengthException.class, () -> userService.registerUser(signupRequestDto), "비밀번호 8~16자리의 영문과 숫자를 조합해주세요.");
                }

                @Test
                @DisplayName("문자만 사용시 예외발생")
                void 비밀번호불량1() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("user@mail.com", "password", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(LengthException.class, () -> userService.registerUser(signupRequestDto), "비밀번호 8~16자리의 영문과 숫자를 조합해주세요.");
                }

                @Test
                @DisplayName("숫자만 사용시 예외발생")
                void 비밀번호불량2() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("user@mail.com", "12345678", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(LengthException.class, () -> userService.registerUser(signupRequestDto), "비밀번호 8~16자리의 영문과 숫자를 조합해주세요.");
                }
            }

            @Nested
            @DisplayName("빈내용")
            class BlankError {

                @Test
                @DisplayName("이메일이 비어있으면 예외발생")
                void 이메일blank() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("", "password486", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(EmptyException.class, () -> userService.registerUser(signupRequestDto), "모든 내용을 입력해주세요.");
                }

                @Test
                @DisplayName("비밀번호가 비어있으면 예외발생")
                void 비밀번호blank() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("user@mail.com", "", "user");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(EmptyException.class, () -> userService.registerUser(signupRequestDto), "모든 내용을 입력해주세요.");
                }

                @Test
                @DisplayName("유저네임이 비어있으면 예외발생")
                void 유저네임blank() {
                    //given
                    SignupRequestDto signupRequestDto = new SignupRequestDto("user@mail.com", "password486", "");

                    Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                            .thenReturn(Optional.empty());
                    //when
                    //then
                    assertThrows(EmptyException.class, () -> userService.registerUser(signupRequestDto), "모든 내용을 입력해주세요.");
                }
            }
        }
    }

    @Nested
    @DisplayName("로그인")
    class SignIn {
        @Test
        @DisplayName("로그인체크")
        void 로그인체크() {
            SignupRequestDto signupRequestDto = new SignupRequestDto("user@mail.com", "password486", "user");
            User user = new User(signupRequestDto);
            UserRequestDto userRequestDto = new UserRequestDto("user@mail.com", "password486");

            Mockito.when(userRepository.findByEmail(userRequestDto.getEmail()))
                    .thenReturn(Optional.of(user));
            Mockito.when(passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword()))
                    .thenReturn(true);

            User result = userService.loginValidCheck(userRequestDto);

            assertThat(result.getEmail()).isEqualTo(userRequestDto.getEmail());
            assertThat(result.getUsername()).isEqualTo(signupRequestDto.getUsername());
        }

        @Nested
        @DisplayName("로그인실패")
        class LoginFail{
            @Nested
            @DisplayName("빈칸")
            class Blank{
                @Test
                @DisplayName("이메일이 비어있으면 예외 발생")
                void 이메일Blank() {
                    UserRequestDto userRequestDto = new UserRequestDto("", "password486");

                    assertThrows(EmptyException.class,() -> userService.loginValidCheck(userRequestDto),"로그인 정보를 모두 입력해주세요.");
                }

                @Test
                @DisplayName("비밀번호가 비어있으면 예외 발생")
                void 비밀번호Blank() {
                    UserRequestDto userRequestDto = new UserRequestDto("user@mail.com", "");

                    assertThrows(EmptyException.class,() -> userService.loginValidCheck(userRequestDto),"로그인 정보를 모두 입력해주세요.");
                }
            }

            @Nested
            @DisplayName("회원정보 불일치")
            class NotMatch {
                @Test
                @DisplayName("비밀번호가 일치하지 않을 경우 예외 발생")
                void 비밀번호불일치() {
                    SignupRequestDto signupRequestDto = new SignupRequestDto("user@mail.com", "password486", "user");
                    User user = new User(signupRequestDto);
                    UserRequestDto userRequestDto = new UserRequestDto("user@mail.com", "asdf1234");

                    Mockito.when(userRepository.findByEmail(userRequestDto.getEmail()))
                            .thenReturn(Optional.of(user));
                    Mockito.when(passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword()))
                            .thenReturn(false);

                    assertThrows(UsernameNotFoundException.class,() -> userService.loginValidCheck(userRequestDto),"회원정보가 일치하지 않습니다. 다시 입력해주세요.");
                }


                @Test
                @DisplayName("이메일이 일치하지 않을 경우 예외 발생")
                void 이메일불일치() {
                    UserRequestDto userRequestDto = new UserRequestDto("username@mail.com", "password486");

                    Mockito.when(userRepository.findByEmail(userRequestDto.getEmail()))
                            .thenReturn(Optional.empty());

                    assertThrows(UsernameNotFoundException.class,() -> userService.loginValidCheck(userRequestDto),"회원정보가 일치하지 않습니다. 다시 입력해주세요.");
                }
            }
        }
    }

    @Nested
    @DisplayName("로그인확인")
    class LoginCheck {

    }
}