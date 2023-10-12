package com.example.bossi.service.order;

import com.example.bossi.dto.order.CompleteOrderRequest;
import com.example.bossi.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public void orderComplete(CompleteOrderRequest dto) {
        // 주문 저장 - 상점이 n개일 경우... 한개씩 저장 구현.

        // 배송정보 저장 - Delivery

        // 주문 생성 - OrderProduct
        // 주문 생성 - OrderProductDetailOption
        // 주문 생성 - Order

        // 포인트는 어떻게 사용해야할까..

    }
}
