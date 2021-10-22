//package com.sparta.kerly_clone.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta.kerly_clone.dto.requestDto.CartRequestDto;
//import com.sparta.kerly_clone.model.Product;
//import com.sparta.kerly_clone.model.User;
//import com.sparta.kerly_clone.repository.CartRepository;
//import com.sparta.kerly_clone.security.WebSecurityConfig;
//import com.sparta.kerly_clone.service.CartService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.web.servlet.MockMvc;
//
//@WebMvcTest( controllers = CartController.class,
//        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class))
//class CartControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private CartService cartService;
//
//    @MockBean
//    private CartRepository cartRepository;
//
//    User testUser;
//    Product mockProduct;
//
//    @BeforeEach
//    public void setup() {
//        testUser = User.builder()
//                .email("testuser@test.com")
//                .password("password")
//                .username("tester").build();
//        mockProduct = Product.builder()
//                .name("상품1")
//                .price(4000L)
//                .description("상품설명")
//                .unit(2)
//                .type("타입 테스트")
//                .delivery("택배")
//                .image("테스트 이미지")
//                .category1("1")
//                .category2("2")
//                .viewCount(3).build();
//    }
//
//    @Nested
//    @DisplayName("장바구니 추가")
//    class addCart {
//
//        @Nested
//        @DisplayName("성공")
//        class addCartSuccess {
//            @Test
//            @WithUserDetails
//            void addCartNormal() {
//                CartRequestDto cartRequestDto = new CartRequestDto();
////                given();
//
//            }
//        }
//
//        @Nested
//        @DisplayName("실패")
//        class addCartFail {
//            @Test
//            @WithUserDetails
//            @DisplayName("실패_로그인 사용자만 이용할 수 있습니다.")
////        NoneLoginException("로그인 사용자만 이용할 수 있습니다.");
////        UsernameNotFoundException("로그인 정보가 존재하지 않습니다.")
////        NoItemException("해당 상품이 존재하지 않습니다.")
//            void addCartError_PossibleOnlyLogin() {
//                CartRequestDto cartRequestDto = new CartRequestDto();
//
//            }
//        }
//
//    }
//
//
//
//
//
//
//
//}