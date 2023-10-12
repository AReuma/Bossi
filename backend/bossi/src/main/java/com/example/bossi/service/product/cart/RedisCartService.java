package com.example.bossi.service.product.cart;

public interface RedisCartService {
    void addToCart(String email, Long productId, String options, String optionCount);

    Integer checkCartCount(String email);
}
