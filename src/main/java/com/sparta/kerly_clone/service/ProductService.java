package com.sparta.kerly_clone.service;

import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final String CLIENT_ID = "BASnL9Xwv5SS9_mW0ZpV";
    private final String CLIENT_SECRET = "GLCkxasRD7";
    private final String OpenNaverMovieUrl_getMovies = "https://openapi.naver.com/v1/search/movie.json?query={keyword}";

    public Page<Product> getProducts(String keyword) {

        if (productRepository.findAll().size() == 0) {
            getProductsFromApi(keyword);
        }

        return null;
    }

    public Page<Product> getProductsFromApi(String keyword) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        headers.add();
        return null;
    }

//    public

}
