package com.example.bossi.repository.order;

import com.example.bossi.entity.OrderProduct;
import com.example.bossi.entity.OrderProductDetailOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductDetailOptionRepository extends JpaRepository<OrderProductDetailOption, Long> {
}
