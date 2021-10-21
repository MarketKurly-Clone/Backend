package com.sparta.kerly_clone.service;

import com.sparta.kerly_clone.dto.requestDto.ReviewRequestDto;
import com.sparta.kerly_clone.dto.responseDto.ReviewListResponseDto;
import com.sparta.kerly_clone.dto.responseDto.ReviewResponseDto;
import com.sparta.kerly_clone.exception.NoItemException;
import com.sparta.kerly_clone.exception.UnauthenticatedException;
import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.model.Review;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.repository.ProductRepository;
import com.sparta.kerly_clone.repository.ReviewRepository;
import com.sparta.kerly_clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public ReviewResponseDto createReview(User user, ReviewRequestDto requestDto) {
        Product product = loadProduct(requestDto.getProductId());          //product 존재하는지 확인
        Review savedReview = reviewRepository.save(new Review(requestDto, user));
        savedReview.addReview(product);

        return new ReviewResponseDto(savedReview, user);
    }

    public ReviewListResponseDto getReviewList(Long productId, int page, int display, User user) {
        Product product = loadProduct(productId);          //product 존재하는지 확인

        PageRequest pageRequest = PageRequest.of(page - 1, display, Sort.by("createdAt").descending());
        List<ReviewResponseDto> reviews = reviewRepository.findAllByProduct(product, pageRequest)
                .stream().map(o -> new ReviewResponseDto(o, user))
                .collect(Collectors.toCollection(ArrayList::new));
        Long reviewCount = reviewRepository.countByProductId(productId);

        return new ReviewListResponseDto(reviews, reviewCount, page, display);
    }

    @Transactional
    public void deleteReview(User user, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NoItemException("해당 댓글이 존재하지 않습니다.")
        );

        if (review.getUser().equals(user)) {
            review.deleteReview();
            reviewRepository.delete(review);
        } else {
            throw new UnauthenticatedException("삭제 권한이 없습니다.");
        }
    }

    private Product loadProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new NoItemException("해당 상품이 존재하지 않습니다.")
        );
    }
}
