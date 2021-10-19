package com.sparta.kerly_clone.dto.requestDto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class CartRequestDto {
    @NotNull(message = "상품을 선택해주세요.")
    private Long productId;
    @NotNull(message = "수량을 입력해주세요.")
    private Long amount;
}
