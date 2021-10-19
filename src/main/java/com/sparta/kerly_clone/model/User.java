package com.sparta.kerly_clone.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
