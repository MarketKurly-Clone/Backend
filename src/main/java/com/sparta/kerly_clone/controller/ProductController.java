package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseDto getProductByQuery(@RequestParam String category1
                                    , @RequestParam String category2
                                    , @RequestParam String query
                                    , @RequestParam int page) {
        log.info("GET, '/products/query', category1={}, category2={}, query={}, page={}", category1, category2, query, page);

        String paramQuery = query.trim();
        Page<Product> products;
        if(paramQuery.equals(""))
            products = productService.getProducts(category1,category2, "스테이크", page);
        else
            products = productService.getProducts(category1,category2, query, page);
        return new ResponseDto("success", "", products);
    }


    @GetMapping("/products/{productId}")
    public ResponseDto getProduct(@PathVariable Long productId) {
        log.info("GET, '/products', productId={}", productId);
        Product product = productService.getProduct(productId);
        return new ResponseDto("success", "", product);
    }
}
