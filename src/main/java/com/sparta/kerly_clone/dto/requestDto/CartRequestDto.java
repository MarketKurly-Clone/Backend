package com.sparta.kerly_clone.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {
    @NotNull(message = "상품을 선택해주세요.")
    private Long productId;
    @NotNull(message = "수량을 입력해주세요.")
    private Long amount;
}
