package com.sparta.kerly_clone.service;

import com.sparta.kerly_clone.dto.requestDto.ProductRequestDto;
import com.sparta.kerly_clone.exception.EmptyException;
import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final String CLIENT_ID = "BASnL9Xwv5SS9_mW0ZpV";
    private final String CLIENT_SECRET = "GLCkxasRD7";
    private final String OpenApiNaverShopUrl = "https://openapi.naver.com/v1/search/shop.json?display=50&query=";
    int display = 20; //1차 프레임에서는 20으로 고정
    int start = 0; // 1차 프레임에서는 0으로 고정
    public Page<Product> getProducts(String category1, String category2, String keyword) {

        Page<Product> products = productRepository.findAll(PageRequest.of(start,display));
        if(products.isEmpty()) {
            String apiBody = getProductsFromApi(keyword);
            List<ProductRequestDto> productApi = fromJSONtoItems(apiBody);
            products = new PageImpl(productApi, PageRequest.of(start,display), productApi.size());

            List<Product> productList = new ArrayList<>();
            for(ProductRequestDto productDto: productApi){
                productList.add(new Product(productDto));
            }
            productRepository.saveAll(productList);
        }
        return products;
    }

    public String getProductsFromApi(String keyword) {
        // 네이버 쇼핑 API 호출에 필요한 Header, Body 정리
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", CLIENT_ID);
        headers.add("X-Naver-Client-Secret", CLIENT_SECRET);
        String body="";

        HttpEntity<String> requestEntity  = new HttpEntity<>(body,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(OpenApiNaverShopUrl+keyword, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        System.out.println("Response Status : " + httpStatus.value());
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    public List<ProductRequestDto> fromJSONtoItems(String result) {
        JSONObject json = new JSONObject(result);
//        start = json.getInt("start"); 2차 프레임시 사용 예정
        display = json.getInt("display"); //2차 프레임시 사용 예정
        JSONArray items = json.getJSONArray("items");
        List<ProductRequestDto> productDtos = new ArrayList<>();
        for(int i=0; i<items.length(); i++) {
            JSONObject itemJson = items.getJSONObject(i);
            ProductRequestDto productDto = new ProductRequestDto(itemJson);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new EmptyException("해당 상품이 존재하지 않습니다."));
    }
}
