package com.example.bossi.service.order;

public interface RedisOrderService {
    void saveOrderNum(String orderNum);

    String getOrderNum(String orderNum);

}
