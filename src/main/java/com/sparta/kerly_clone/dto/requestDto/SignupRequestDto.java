package com.sparta.kerly_clone.dto.requestDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequestDto {
    String email;
    String password;
    String username;
}
