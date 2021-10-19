package com.sparta.kerly_clone.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    private Long productId;
    private String title;
    private String content;
}
