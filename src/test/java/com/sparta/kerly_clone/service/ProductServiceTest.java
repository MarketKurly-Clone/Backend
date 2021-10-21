package com.sparta.kerly_clone.service;

import com.sparta.kerly_clone.dto.requestDto.ProductRequestDto;
import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("상품 리스트 조회")
    void 상품리스트(){
        //given
        int start = 0;
        int display = 15;
        List<Product> productList = new ArrayList<>();
        for(int i = 0 ;i < 15; i++){
            ProductRequestDto productRequestDto = new ProductRequestDto(
                    "product" + (i + 1),
                    (i + 1) * 10000L,
                    "description" + (i + 1),
                    "category1/" + (i + 1),
                    "category2/" + (i + 1),
                    "image" + (i + 1)
            );
            Product product = new Product(productRequestDto);
            productList.add(product);
        }
        Page<Product> products = new PageImpl<>(productList,PageRequest.of(start, display), productList.size());
        ProductService productService = new ProductService(productRepository);
        Mockito.when(productRepository.findAll(PageRequest.of(start, display))).thenReturn(products);

        //when
        Page<Product> result = productService.getProducts("", "", "", 0);
        //then
        assertEquals(result.getContent().get(0).getName(), "product1");
        assertEquals(result.getContent().get(0).getPrice(), 10000L);
        assertEquals(result.getContent().get(0).getDescription(), "description1");
        assertEquals(result.getContent().get(0).getCategory1(), "category1/1");
        assertEquals(result.getContent().get(0).getCategory2(), "category2/1");
        assertEquals(result.getContent().get(0).getImage(), "image1");
    }

    @Test
    @DisplayName("상품 상세조회")
    void 상품상세조회(){
        //given
        Long productId = 100L;
        ProductRequestDto productRequestDto = new ProductRequestDto(
                "title",
                10000L,
                "description",
                "category1",
                "category2",
                "image"
        );

        Product product = new Product(productRequestDto);
        ProductService productService = new ProductService(productRepository);
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        //when
        Product result = productService.getProduct(productId);
        //then
        assertEquals(result.getName(), product.getName());
    }
}