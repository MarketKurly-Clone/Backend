package com.sparta.kerly_clone.repository;

import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByProduct(Product product, Pageable pageable);

    Long countByProductId(Long productId);
}
