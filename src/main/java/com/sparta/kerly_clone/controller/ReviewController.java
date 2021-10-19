package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.requestDto.ReviewRequestDto;
import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import com.sparta.kerly_clone.dto.responseDto.ReviewListResponseDto;
import com.sparta.kerly_clone.dto.responseDto.ReviewResponseDto;
import com.sparta.kerly_clone.exception.NoneLoginException;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.security.UserDetailsImpl;
import com.sparta.kerly_clone.service.ReviewService;
import com.sparta.kerly_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostMapping("/reviews")
    public ResponseDto createReview(@RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails == null){
            throw new NoneLoginException("로그인 사용자만 이용할 수 있습니다.");
        }
        User user = userService.loadUserEamil(userDetails.getUsername());
        ReviewResponseDto responseDto = reviewService.createReview(user, requestDto);
        return new ResponseDto("success", "성공적으로 댓글이 추가되었습니다.", responseDto);
    }

    @GetMapping("/reviews")
    public ResponseDto getReviews(@RequestParam("productId") Long productId, @RequestParam("page") int page, @RequestParam("display") int display) {
        ReviewListResponseDto responseDto = reviewService.getReviewList(productId, page, display);
        return new ResponseDto("success", "성공적으로 댓글이 조회되었습니다.", responseDto);
    }

    @DeleteMapping("/reviews")
    public ResponseDto deleteReview(@RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails == null){
            throw new NoneLoginException("로그인 사용자만 이용할 수 있습니다.");
        }
        User user = userService.loadUserEamil(userDetails.getUsername());
        reviewService.deleteReview(user, requestDto.getProductId(), requestDto.getReviewId());

        return new ResponseDto("success", "성공적으로 댓글이 삭제되었습니다.", "");
    }
}
