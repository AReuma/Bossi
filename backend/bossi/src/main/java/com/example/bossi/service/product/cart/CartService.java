package com.example.bossi.service.product.cart;

import com.example.bossi.response.product.cart.DirectButOrderItemInfo;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<DirectButOrderItemInfo> directBuyOrderList(Long productId, String options, String optionCount);
}
