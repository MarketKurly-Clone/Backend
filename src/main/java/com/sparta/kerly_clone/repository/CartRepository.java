package com.sparta.kerly_clone.repository;

import com.sparta.kerly_clone.model.Cart;
import com.sparta.kerly_clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Long countByUser(User user);

    Optional<Cart> findByUserIdAndProductId(Long userId, Long productId);

    List<Cart> findAllByUserOrderByAddedAtDesc(User user);
}
