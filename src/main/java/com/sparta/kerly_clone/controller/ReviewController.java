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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
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
        log.info("POST, '/reviews', productId={}, title={}, content={}", requestDto.getProductId(), requestDto.getTitle(), requestDto.getContent());
        if (userDetails == null) {
            throw new NoneLoginException("로그인 사용자만 이용할 수 있습니다.");
        }
        User user = userService.loadUserEamil(userDetails.getUsername());
        ReviewResponseDto responseDto = reviewService.createReview(user, requestDto);
        return new ResponseDto("success", "성공적으로 댓글이 추가되었습니다.", responseDto);
    }

    @GetMapping("/reviews")
    public ResponseDto getReviews(@RequestParam("productId") Long productId, @RequestParam("page") int page, @RequestParam("display") int display, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("GET, '/reviews', productId={}, page={}, display={}", productId, page, display);
        ReviewListResponseDto responseDto;
        if (userDetails == null) {
            responseDto = reviewService.getReviewList(productId, page, display, new User());
        } else {
            User user = userService.loadUserEamil(userDetails.getUsername());
            responseDto = reviewService.getReviewList(productId, page, display, user);
        }
        return new ResponseDto("success", "성공적으로 댓글이 조회되었습니다.", responseDto);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseDto deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("DELETE, '/reviews', reviewId={}", reviewId);
        if (userDetails == null) {
            throw new NoneLoginException("로그인 사용자만 이용할 수 있습니다.");
        }
        User user = userService.loadUserEamil(userDetails.getUsername());
        reviewService.deleteReview(user, reviewId);

        return new ResponseDto("success", "성공적으로 댓글이 삭제되었습니다.", "");
    }
}
