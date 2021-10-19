package com.sparta.kerly_clone.model;

import com.sparta.kerly_clone.dto.requestDto.ReviewRequestDto;
import com.sparta.kerly_clone.util.Timestamped;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Review extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Column(columnDefinition = "long default 0L")
    private Long likedCount;

    public Review(ReviewRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.likedCount = 0L;
        this.user = user;
    }

    public void addReview(Product product) {
        this.product = product;
        product.getReviews().add(this);
    }
}
