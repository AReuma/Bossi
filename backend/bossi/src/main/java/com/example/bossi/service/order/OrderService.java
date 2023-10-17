package com.example.bossi.service.order;

import com.example.bossi.dto.order.CompleteOrderMultiProductRequest;
import com.example.bossi.dto.order.CompleteOrderRequest;
import com.example.bossi.response.order.OrderProductInfoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    String orderComplete(CompleteOrderRequest dto);

    ResponseEntity<OrderProductInfoResponse> showOrderComplete(String orderNum);

    String multiOrderComplete(CompleteOrderMultiProductRequest completeOrderMultiRequest) throws JsonProcessingException;
}
