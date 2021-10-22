package com.sparta.kerly_clone.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeRequestDto {
    @Schema(description = "리뷰 ID")
    private Long reviewId;
}
