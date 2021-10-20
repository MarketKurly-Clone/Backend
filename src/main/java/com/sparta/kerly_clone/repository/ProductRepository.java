package com.sparta.kerly_clone.repository;

import com.sparta.kerly_clone.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAll(Pageable pageable);
}
