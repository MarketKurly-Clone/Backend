package com.sparta.kerly_clone.repository;

import com.sparta.kerly_clone.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select a from Product a where a.name like %?1%")
    Page<Product> findAllByQuery(Pageable pageable, String query);
//    Page<Product> findAll(Pageable pageable);
}
