package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.requestDto.ReviewRequestDto;
import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import com.sparta.kerly_clone.dto.responseDto.ReviewListResponseDto;
import com.sparta.kerly_clone.dto.responseDto.ReviewResponseDto;
import com.sparta.kerly_clone.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/reviews")
    public ResponseDto createReview(@RequestBody ReviewRequestDto requestDto) {
        ReviewResponseDto responseDto = reviewService.createReview(requestDto);
        return new ResponseDto("success", "성공적으로 댓글이 추가되었습니다.", responseDto);
    }

    @GetMapping("/reviews")
    public ResponseDto getReviews(@RequestParam Long productId, @RequestParam int page, @RequestParam int display){
        ReviewListResponseDto responseDto = reviewService.getReviewList(productId, page, display);
        return new ResponseDto("success", "성공적으로 댓글이 조회되었습니다.", responseDto);
    }

    @DeleteMapping("/reviews")
    public ResponseDto deleteReview(@RequestBody Map<String, Long> map) {
        Long productId = map.get("productId");
        Long reviewId = map.get("reviewId");
        reviewService.deleteReview(productId, reviewId);

        return new ResponseDto("success", "성공적으로 댓글이 삭제되었습니다.", "");
    }
}
