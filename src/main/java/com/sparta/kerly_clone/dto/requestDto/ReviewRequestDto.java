package com.sparta.kerly_clone.dto.requestDto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class ReviewRequestDto {
    @NotNull(message = "상품을 선택해주세요.")
    private Long productId;
    private Long reviewId;
    @NotNull(message = "제목을 입력해주세요.")
    private String title;
    @NotNull(message = "내용을 작성해주세요.")
    private String content;
}
