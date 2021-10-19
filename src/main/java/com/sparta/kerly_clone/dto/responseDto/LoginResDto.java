package com.sparta.kerly_clone.dto.responseDto;

import com.sparta.kerly_clone.model.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResDto {
    String token;
    User user;
}
