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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name = "Review Controller Api V1")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @Operation(summary = "리뷰 작성")
    @PostMapping("/reviews")
    public ResponseDto createReview(@RequestBody ReviewRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, '/reviews', productId={}, title={}, content={}", requestDto.getProductId(), requestDto.getTitle(), requestDto.getContent());
        if (userDetails == null) {
            throw new NoneLoginException("로그인 사용자만 이용할 수 있습니다.");
        }
        User user = userService.loadUserEamil(userDetails.getUsername());
        ReviewResponseDto responseDto = reviewService.createReview(user, requestDto);
        return new ResponseDto("success", "성공적으로 댓글이 추가되었습니다.", responseDto);
    }

    @Operation(summary = "리뷰 조회")
    @GetMapping("/reviews")
    public ResponseDto getReviews(@Parameter(name = "productId", description = "제품 ID", example = "1") @RequestParam("productId") Long productId, @Parameter(name = "page", description = "페이지 번호", example = "1") @RequestParam("page") int page, @Parameter(name = "display", description = "페이지당 표시 갯수", example = "10") @RequestParam("display") int display, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
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

    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseDto deleteReview(@Parameter(name = "reviewId",description = "리뷰 ID") @PathVariable Long reviewId, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("DELETE, '/reviews', reviewId={}", reviewId);
        if (userDetails == null) {
            throw new NoneLoginException("로그인 사용자만 이용할 수 있습니다.");
        }
        User user = userService.loadUserEamil(userDetails.getUsername());
        reviewService.deleteReview(user, reviewId);

        return new ResponseDto("success", "성공적으로 댓글이 삭제되었습니다.", "");
    }
}
