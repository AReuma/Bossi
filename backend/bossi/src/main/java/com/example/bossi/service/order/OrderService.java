package com.example.bossi.service.order;

import com.example.bossi.dto.order.CompleteOrderRequest;

public interface OrderService {
    void orderComplete(CompleteOrderRequest dto);
}
