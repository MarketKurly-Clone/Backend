package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.requestDto.SignupRequestDto;
import com.sparta.kerly_clone.dto.requestDto.UserRequestDto;
import com.sparta.kerly_clone.dto.responseDto.LoginResDto;
import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import com.sparta.kerly_clone.exception.JwtTokenExpiredException;
import com.sparta.kerly_clone.exception.UnauthenticatedException;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.security.JwtTokenProvider;
import com.sparta.kerly_clone.security.UserDetailsImpl;
import com.sparta.kerly_clone.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseDto createUser(@RequestBody SignupRequestDto signupRequestDto) {
        log.info("POST, '/user/register', email={}, password={}, username={}",
                signupRequestDto.getEmail(),signupRequestDto.getPassword(), signupRequestDto.getUsername());
        String result = "";
        String msg = "";
        if (userService.registerUser(signupRequestDto)) {
            result = "success";
            msg = "성공적으로 회원가입되었습니다.";
        }
        return new ResponseDto(result, msg, "");
    }

    @GetMapping("/register")
    public ResponseDto dupCheckEmail(@RequestParam String email) {
        log.info("GET, '/user/register', email={}", email);
        String result = "";
        String msg = "";
        if (userService.checkDupEmail(email)) {
            result = "success";
            msg = "사용가능한 아이디입니다.";
        }
        return new ResponseDto(result, msg, "");
    }

    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserRequestDto userRequestDto) {
        log.info("POST, '/user/login', email={}, password={}", userRequestDto.getEmail(), userRequestDto.getPassword());
        User user = userService.loginValidCheck(userRequestDto);
        String token = userService.createToken(userRequestDto);
        LoginResDto loginResDto = LoginResDto.builder().token(token).user(user).build();
        return new ResponseDto("success", "로그인에 성공하였습니다.", loginResDto);
    }

    @GetMapping("/info")
    public ResponseDto getUserInfoFromToken(@RequestHeader(value = "authorization") String token) {
        log.info("GET, '/user/info', token={}", token);
        if (jwtTokenProvider.validateToken(token)) {
            return new ResponseDto("success", "유저정보를 성공적으로 불러왔습니다.", getLoginResDtoFromToken(token));
        } else
            throw new JwtTokenExpiredException("토큰이 만료되었습니다.");
    }

    private LoginResDto getLoginResDtoFromToken(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            return getLoginResDtoFromPrincipal((UserDetailsImpl) principal, token);
        } else {
            log.info("유효하지 않은 토큰입니다.");
            throw new UnauthenticatedException("유효하지 않은 토큰입니다.");
        }
    }

    private LoginResDto getLoginResDtoFromPrincipal(UserDetailsImpl principal, String token) {
        User user = principal.getUser();
        return LoginResDto.builder().token(token).user(user).build();
    }
}
