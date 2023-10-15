package com.example.bossi.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisOrderServiceImpl implements RedisOrderService{

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveOrderNum(String orderNum) {
        redisTemplate.opsForValue().set("order:"+orderNum, orderNum, Duration.ofMinutes(2));
    }

    @Override
    public String getOrderNum(String orderNum) {
        //String orderNumber = (String) redisTemplate.opsForValue().get("order:" + orderNum);
        return (String) redisTemplate.opsForValue().get("order:" + orderNum);
    }
}
