package com.sparta.kerly_clone.dto.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewListResponseDto {
    private List<ReviewResponseDto> reviews;
    private Long reviewCount;

    public ReviewListResponseDto(List<ReviewResponseDto> reviewList, Long reviewCount) {
        this.reviews = reviewList;
        this.reviewCount = reviewCount;
    }
}