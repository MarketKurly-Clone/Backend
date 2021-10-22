package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.requestDto.LikeRequestDto;
import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import com.sparta.kerly_clone.exception.NoneLoginException;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.security.UserDetailsImpl;
import com.sparta.kerly_clone.service.LikedService;
import com.sparta.kerly_clone.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Slf4j
public class LikedController {

    private final LikedService likedService;
    private final UserService userService;

    @Autowired
    public LikedController(LikedService likedService, UserService userService) {
        this.likedService = likedService;
        this.userService = userService;
    }

    @PostMapping("liked")
    public ResponseDto liked(@RequestBody LikeRequestDto requestDto, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, '/liked', reviewId={}", requestDto.getReviewId());
        if (userDetails == null) {
            throw new NoneLoginException("로그인 사용자만 이용할 수 있습니다.");
        }
        User user = userService.loadUserEamil(userDetails.getUsername());
        if (likedService.likedReview(requestDto, user)) {
            return new ResponseDto("success", "좋아요를 하였습니다.", "");
        }
        return new ResponseDto("success", "좋아요를 취소하였습니다.", "");
    }
}
