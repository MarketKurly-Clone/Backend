package com.sparta.kerly_clone.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private String result;
    private String message;
    private Object data;
}
