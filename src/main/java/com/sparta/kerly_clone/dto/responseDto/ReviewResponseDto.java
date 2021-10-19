package com.sparta.kerly_clone.dto.responseDto;

import com.sparta.kerly_clone.model.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponseDto {
    private Long reviewId;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long likedCount;

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getId();
//        this.username = review.getUser().getUsername();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
        this.likedCount = review.getLikedCount();
    }
}
