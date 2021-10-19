package com.sparta.kerly_clone.dto.responseDto;

import com.sparta.kerly_clone.model.Cart;
import com.sparta.kerly_clone.model.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponseDto {
    private Long productId;
    private String name;
    private String category1;
    private String category2;
    private String image;
    private String description;
    private Long price;
    private int viewCount;

    public ProductResponseDto(Cart cart) {
        Product product = cart.getProduct();
        this.productId = product.getId();
        this.name = product.getName();
        this.category1 = product.getCategory1();
        this.category2 = product.getCategory2();
        this.image = product.getImage();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.viewCount = product.getViewCount();
    }
}
