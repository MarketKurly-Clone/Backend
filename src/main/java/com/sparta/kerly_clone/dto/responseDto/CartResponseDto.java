package com.sparta.kerly_clone.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CartResponseDto {
    private List<ProductResponseDto> products;
    private Long itemCount;

    public CartResponseDto(List<ProductResponseDto> responseDtoList, Long itemCount) {
        this.products = responseDtoList;
        this.itemCount = itemCount;
    }
}
