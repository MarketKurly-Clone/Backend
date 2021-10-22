package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product Controller Api V1")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 목록 조회")
    @GetMapping("/products")
    public ResponseDto getProductByQuery(
            @Parameter(name = "category1", in = ParameterIn.QUERY, description = "대분류", allowEmptyValue = true) @RequestParam String category1,
            @Parameter(name = "category2", in = ParameterIn.QUERY, description = "중분류", allowEmptyValue = true) @RequestParam String category2,
            @Parameter(name = "query", in = ParameterIn.QUERY, description = "검색어", allowEmptyValue = true) @RequestParam String query,
            @Parameter(name = "page", in = ParameterIn.QUERY, description = "페이지 번호", example = "1") @RequestParam int page) {
        log.info("GET, '/products/query', category1={}, category2={}, query={}, page={}", category1, category2, query, page);

        String paramQuery = query.trim();
        Page<Product> products;
        if (paramQuery.equals(""))
            products = productService.getProducts(category1, category2, "스테이크", page);
        else
            products = productService.getProducts(category1, category2, query, page);
        return new ResponseDto("success", paramQuery, products);
    }

    @Operation(summary = "상품 상세조회")
    @GetMapping("/products/{productId}")
    public ResponseDto getProduct(@Parameter(name = "productId", in = ParameterIn.PATH, description = "상품 ID") @PathVariable Long productId) {
        log.info("GET, '/products', productId={}", productId);
        Product product = productService.getProduct(productId);
        return new ResponseDto("success", "", product);
    }
}
