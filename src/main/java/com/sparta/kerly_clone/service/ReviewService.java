package com.sparta.kerly_clone.service;

import com.sparta.kerly_clone.dto.requestDto.ReviewRequestDto;
import com.sparta.kerly_clone.dto.responseDto.ReviewResponseDto;
import com.sparta.kerly_clone.exception.CustomErrorException;
import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.model.Review;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.repository.ProductRepository;
import com.sparta.kerly_clone.repository.ReviewRepository;
import com.sparta.kerly_clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(UserRepository userRepository, ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto requestDto) {
        User user = userRepository.findById(1L).orElseThrow(
                () -> new CustomErrorException("존재하지 않는 사용자입니다.")
        );
        Product product = loadProduct(requestDto.getProductId());          //product 존재하는지 확인
        Review savedReview = reviewRepository.save(new Review(requestDto, user));
        savedReview.addReview(product);

        return new ReviewResponseDto(savedReview);
    }

    private Product loadProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new CustomErrorException("해당 게시물이 존재하지 않습니다.")
        );
    }
}
