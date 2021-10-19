package com.sparta.kerly_clone.service;

import com.sparta.kerly_clone.dto.requestDto.CartRequestDto;
import com.sparta.kerly_clone.exception.NoItemException;
import com.sparta.kerly_clone.model.Cart;
import com.sparta.kerly_clone.model.Product;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.repository.CartRepository;
import com.sparta.kerly_clone.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Long addProduct(CartRequestDto requestDto, User user) {
        Product product = loadProduct(requestDto.getProductId());
        Cart cart = cartRepository.findByUserIdAndProduct(user.getId(), product).orElse(null);
        if(cart == null){
            cart = new Cart();
            cart.addNewProduct(product, requestDto.getAmount(), user);
        } else {
            cart.addProductAmount(requestDto.getAmount());
        }
        cartRepository.save(cart);
        return cartRepository.countByUser(user);
    }

    private Product loadProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new NoItemException("해당 상품이 존재하지 않습니다.")
        );
    }
}
