package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.requestDto.CartRequestDto;
import com.sparta.kerly_clone.dto.responseDto.CartResponseDto;
import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import com.sparta.kerly_clone.exception.NoneLoginException;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.security.UserDetailsImpl;
import com.sparta.kerly_clone.service.CartService;
import com.sparta.kerly_clone.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@Tag(name = "Cart Controller Api V1")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @Operation(summary = "장바구니 추가")
    @PostMapping("/cart")
    public ResponseDto addCart(@Valid @RequestBody CartRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, '/cart', productId={}, amount={}", requestDto.getProductId(), requestDto.getAmount());
        if (userDetails == null) {
            throw new NoneLoginException("로그인 사용자만 이용할 수 있습니다.");
        }
        User user = userService.loadUserEamil(userDetails.getUsername());
        Long itemCount = cartService.addProduct(requestDto, user);
        Map<String, Long> responseMap = new HashMap<>();
        responseMap.put("itemCount", itemCount);

        return new ResponseDto("success", "장바구니에 추가 되었습니다", responseMap);
    }

    @Operation(summary = "장바구니 조회")
    @GetMapping("/cart")
    public ResponseDto getCart(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("GET, '/cart'");
        if (userDetails == null) {
            throw new NoneLoginException("로그인 사용자만 이용할 수 있습니다.");
        }
        User user = userService.loadUserEamil(userDetails.getUsername());
        CartResponseDto responseDto = cartService.getCart(user);

        return new ResponseDto("success", "성공적으로 조회 되었습니다", responseDto);
    }

    @Operation(summary = "장바구니 삭제")
    @DeleteMapping("/cart/{productId}")
    public ResponseDto deleteCart(@Parameter(name = "productId", in = ParameterIn.PATH, description = "상품 ID") @PathVariable Long productId, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("DELETE, '/cart', productId={}", productId);
        User user = userService.loadUserEamil(userDetails.getUsername());
        cartService.deleteCart(user, productId);
        return new ResponseDto("success", "성공적으로 삭제되었습니다.", "");
    }
}

