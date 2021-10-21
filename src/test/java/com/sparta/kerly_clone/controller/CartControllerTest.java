package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.security.WebSecurityConfig;
import com.sparta.kerly_clone.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest( controllers = CartController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class))
class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartService cartService;

    User testUser;
    Product mockProduct;

    @BeforeEach
    public void setup() {
        testUser = User.builder()
                .email("testuser@test.com")
                .password("password")
                .username("tester").build();
        mockProduct = Product.builder()
                .name("상품1")
                .price(4000)
                .description("상품설명")
                .unit(2)
                .type("타입 테스트")
                .delivery("택배")
                .image("테스트 이미지")
                .category1(1)
                .category2(2)
                .viewCount(3).build();
    }

    @Test
    @WithUserDetails
    @DisplayName("장바구니 추가 확인 _ 성공")
    void addCartNormal() {

    }






}