package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.filter.MockSpringSecurityFilter;
import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.repository.ProductRepository;
import com.sparta.kerly_clone.security.JwtAuthenticationFilter;
import com.sparta.kerly_clone.security.WebSecurityConfig;
import com.sparta.kerly_clone.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(
        controllers = {ProductController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class ProductControllerTest {
    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    ProductService productService;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    private Page<Product> products;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();

        mockProductSetup();
    }

    private void mockProductSetup() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product("고구마", 10000L, "신선한 고구마", "채소", "고구마", "imageUrl", 0);
        Product product2 = new Product("감자", 8000L, "커다란 감자", "채소", "감자", "imageUrl", 0);
        Product product3 = new Product("삼겹살", 15000L, "냉동 삼겹살", "육류", "돼지고기", "imageUrl", 0);
        Product product4 = new Product("한우", 20000L, "최고급 한우", "육류", "소고기", "imageUrl", 0);
        Product product5 = new Product("상추", 3000L, "신선한 상추", "채소", "상추", "imageUrl", 0);
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);

        products = new PageImpl<>(productList);
    }

    @Test
    @DisplayName("상품 리스트 조회")
    void test1() throws Exception {
        // given
        MultiValueMap<String, String> signupRequestForm = new LinkedMultiValueMap<>();
        signupRequestForm.add("category1", "");
        signupRequestForm.add("category2", "");
        signupRequestForm.add("query", "");
        signupRequestForm.add("page", "1");

        when(productService.getProducts("", "", "스테이크", 0)).thenReturn(products);
        // when - then
        ResultActions resultActions = mvc.perform(get("/products")
                        .params(signupRequestForm)
                )
                .andDo(print());

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}