package com.example.bossi.service.product.cart;

import com.example.bossi.response.product.cart.CartProductResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface RedisCartService {
    void addToCart(String email, Long productId, String options, String optionCount);

    Integer checkCartCount(String email);

    List<CartProductResponse> showCartProduct(String email) throws JsonProcessingException;
}
