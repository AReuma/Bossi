package com.example.bossi.service.product.cart;

import com.example.bossi.response.product.cart.DirectButOrderItemInfo;
import com.example.bossi.response.product.cart.OrderProductInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    ResponseEntity<DirectButOrderItemInfo> directBuyOrderList(Long productId, String options, String optionCount);

    ResponseEntity<String> modifyDirectOption(List<String> options);

    ResponseEntity<OrderProductInfo> orderProduct(Long productId, String options, String optionCount, String email);
}
