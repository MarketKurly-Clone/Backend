package com.sparta.kerly_clone.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long price;

    @Column
    private String description;

    @Column
    private int unit;

    @Column
    private String delivery;

    @Column
    private String category1;

    @Column
    private String category2;

    @Column
    private String image;

    @Column
    private int viewCount;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    public Product(String name, Long price, String description, int unit, String delivery, String category1, String category2, String image,int viewCount) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.unit = unit;
        this.delivery = delivery;
        this.image = image;
        this.category1 = category1;
        this.category2 = category2;
        this.viewCount = viewCount;
    }

    public void deleteReview(Review review) {
        this.reviews.remove(review);
    }
}
