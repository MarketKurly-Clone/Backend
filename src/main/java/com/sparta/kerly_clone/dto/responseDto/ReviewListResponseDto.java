package com.sparta.kerly_clone.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReviewListResponseDto {
    private List<ReviewResponseDto> reviews;
    private Long reviewCount;

    public ReviewListResponseDto(List<ReviewResponseDto> reviewList, Long reviewCount) {
        this.reviews = reviewList;
        this.reviewCount = reviewCount;
    }
}