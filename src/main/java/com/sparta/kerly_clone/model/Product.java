package com.sparta.kerly_clone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.kerly_clone.dto.requestDto.ProductRequestDto;
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
    private String type;

    @Column
    private int viewCount;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
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

    public Product(ProductRequestDto productDto) {
        this.name = productDto.getTitle();
        this.price = productDto.getPrice();
        this.description = productDto.getDescription();
        this.unit = 1;
        this.delivery = "샛별배송/택배배송";
        this.image = productDto.getImage();
        this.category1 = productDto.getCategory1();
        this.category2 = productDto.getCategory2();
        this.viewCount = 0;
    }
    public void deleteReview(Review review) {
        this.reviews.remove(review);
    }
}
