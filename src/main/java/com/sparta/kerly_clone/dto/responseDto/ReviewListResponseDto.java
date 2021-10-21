package com.sparta.kerly_clone.dto.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewListResponseDto {
    private List<ReviewResponseDto> reviews;
    private Long reviewCount;
    private int totalPage;
    private int nowPage;

    public ReviewListResponseDto(List<ReviewResponseDto> reviewList, Long reviewCount, int page, int display) {
        this.reviews = reviewList;
        this.reviewCount = reviewCount;
        this.nowPage = page;
        this.totalPage = reviewCount > 0 ? (int)((reviewCount - 1)/display) + 1 : 1;
    }
}