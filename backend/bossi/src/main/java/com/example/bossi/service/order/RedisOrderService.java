package com.example.bossi.service.order;

import com.example.bossi.dto.order.RedisOrderProductInfo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface RedisOrderService {
    void saveOrderNum(String orderNum);

    String getOrderNum(String orderNum);

    List<RedisOrderProductInfo> orderProductInfo(List<Long> productIdList, String email) throws JsonProcessingException;

    void removerProduct(List<Long> selectProductId, String email);
}
