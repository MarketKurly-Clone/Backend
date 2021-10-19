package com.sparta.kerly_clone.dto.responseDto;

import com.sparta.kerly_clone.model.User;
import lombok.Builder;

@Builder
public class LoginResDto {
    String token;
    User user;
}
