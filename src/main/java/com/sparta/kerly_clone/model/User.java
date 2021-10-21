package com.sparta.kerly_clone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.kerly_clone.dto.requestDto.SignupRequestDto;
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
    @JsonIgnore
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Cart> cartList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Liked> likedList = new ArrayList<>();

    public User(SignupRequestDto signupRequestDto) {
        this.email = signupRequestDto.getEmail();
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
    }
}
