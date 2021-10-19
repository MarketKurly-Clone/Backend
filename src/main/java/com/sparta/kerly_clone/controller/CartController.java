package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.requestDto.CartRequestDto;
import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.service.CartService;
import com.sparta.kerly_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping("/cart")
    public ResponseDto addProduct(@Valid @RequestBody CartRequestDto requestDto) {
        User user = userService.loadUser(1L);
        Long itemCount = cartService.addProduct(requestDto, user);
        Map<String, Long> responseMap = new HashMap<>();
        responseMap.put("itemCount", itemCount);

        return new ResponseDto("success", "장바구니에 추가 되었습니다", responseMap);
    }
}

