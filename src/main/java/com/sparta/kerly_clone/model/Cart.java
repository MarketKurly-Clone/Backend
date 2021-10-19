package com.sparta.kerly_clone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Long amount;
    private LocalDateTime addedAt;

    public void addProductAmount(Long amount) {
        this.amount += amount;
        this.addedAt = LocalDateTime.now();
        user.getCartList().add(this);
    }
    public void addNewProduct(Product product, Long amount, User user) {
        this.product = product;
        this.amount = amount;
        this.user = user;
        user.getCartList().add(this);
    }
}