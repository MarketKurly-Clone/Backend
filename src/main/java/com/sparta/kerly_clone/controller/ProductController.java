package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseDto getProducts(@RequestParam String category1, @RequestParam String category2) {
        Page<Product> products = productService.getProducts(category1, category2, "야채");
        return new ResponseDto("success", "", products);
    }

    @GetMapping("/products/{productId}")
    public ResponseDto getProduct(@PathVariable Long productId) {
        Product product = productService.getProduct(productId);
        return new ResponseDto("success", "", product);
    }
}
