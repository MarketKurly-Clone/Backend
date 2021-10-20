package com.sparta.kerly_clone.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    @NotNull(message = "상품을 선택해주세요.")
    private Long productId;
    @NotNull(message = "제목을 입력해주세요.")
    private String title;
    @NotNull(message = "내용을 작성해주세요.")
    private String content;
}
