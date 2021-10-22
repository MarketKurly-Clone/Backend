package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "Product Controller Api V1")
public class ProductController {

    private final ProductService productService;

    @ApiOperation(value = "상품 목록 조회")
    @GetMapping("/products")
    public ResponseDto getProductByQuery(
                                  @ApiParam(value = "대분류", required = false) @RequestParam String category1
                                , @ApiParam(value = "중분류", required = false)@RequestParam String category2
                                , @ApiParam(value = "검색어", required = true) @RequestParam String query
                                , @ApiParam(value = "페이지 번호", required = true) @RequestParam int page) {
        log.info("GET, '/products/query', category1={}, category2={}, query={}, page={}", category1, category2, query, page);

        String paramQuery = query.trim();
        Page<Product> products;
        if(paramQuery.equals(""))
            products = productService.getProducts(category1,category2, "스테이크", page);
        else
            products = productService.getProducts(category1,category2, query, page);
        return new ResponseDto("success", "", products);
    }

    @ApiOperation(value = "상품 상세조회")
    @GetMapping("/products/{productId}")
    public ResponseDto getProduct( @ApiParam(value = "상품 ID", required = true) @PathVariable Long productId) {
        log.info("GET, '/products', productId={}", productId);
        Product product = productService.getProduct(productId);
        return new ResponseDto("success", "", product);
    }
}
