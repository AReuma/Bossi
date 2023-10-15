package com.example.bossi.service.order;

import com.example.bossi.dto.order.CompleteOrderRequest;
import com.example.bossi.response.order.OrderProductInfoResponse;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    void orderComplete(CompleteOrderRequest dto);

    ResponseEntity<OrderProductInfoResponse> showOrderComplete(String orderNum);
}
