package com.sparta.kerly_clone.repository;

import com.sparta.kerly_clone.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
