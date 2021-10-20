package com.sparta.kerly_clone.repository;

import com.sparta.kerly_clone.model.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    Optional<Liked> findByUserIdAndReviewId(Long userId, Long reviewId);
}
