package com.sparta.kerly_clone.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequestDto {
    private String email;
    private String password;
}
