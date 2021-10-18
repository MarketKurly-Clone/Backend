package com.sparta.kerly_clone.model;

import lombok.*;

import javax.persistence.*;

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
    private int viewCount;

}
