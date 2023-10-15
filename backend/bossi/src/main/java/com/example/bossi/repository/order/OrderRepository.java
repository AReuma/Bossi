package com.example.bossi.repository.order;

import com.example.bossi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderNum(String orderNum);
}
