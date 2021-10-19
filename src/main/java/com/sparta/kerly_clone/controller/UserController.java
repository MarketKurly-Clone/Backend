package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.SignupRequestDto;
import com.sparta.kerly_clone.dto.UserRequestDto;
import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import com.sparta.kerly_clone.exception.JwtTokenExpiredException;
import com.sparta.kerly_clone.exception.UnauthenticatedException;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.security.JwtTokenProvider;
import com.sparta.kerly_clone.security.UserDetailsImpl;
import com.sparta.kerly_clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseDto createUser(@RequestBody SignupRequestDto signupRequestDto) {
        String result = "";
        String msg = "";
        if(userService.registerUser(signupRequestDto)) {
            result = "success";
            msg = "성공적으로 회원가입되었습니다.";
        }
        return new ResponseDto(result, msg, "");
    }

    @GetMapping("/register")
    public ResponseDto dupCheckEmail(@RequestParam String email) {
        String result = "";
        String msg = "";
        if(userService.checkDupEmail(email)){
            result = "success";
            msg = "사용가능한 아이디입니다.";
        }
        return new ResponseDto(result, msg, "");
    }

    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserRequestDto userRequestDto) {
        String token = userService.createToken(userRequestDto);
        return new ResponseDto("success", "로그인에 성공하였습니다.", token);
    }

    @GetMapping("/info")
    public ResponseDto getUserInfoFromToken(@RequestHeader(value="X-AUTH-TOKEN") String token) {
        UserRequestDto userRequestDto;
        if(jwtTokenProvider.validateToken(token)) {
            userRequestDto = getLoginResDtoFromToken(token);
            return new ResponseDto("success", "유저정보를 성공적으로 불러왔습니다.", userRequestDto);
        }
        else
            throw new JwtTokenExpiredException("토큰이 만료되었습니다.");
    }

    private UserRequestDto getLoginResDtoFromToken(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        Object principal = authentication.getPrincipal();
        if ( principal instanceof UserDetailsImpl) {
            return getLoginResDtoFromPrincipal((UserDetailsImpl) principal);
        } else {
            throw new UnauthenticatedException("유효하지 않은 토큰입니다.");
        }
    }

    private UserRequestDto getLoginResDtoFromPrincipal(UserDetailsImpl principal) {
        User user = principal.getUser();
        return UserRequestDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
