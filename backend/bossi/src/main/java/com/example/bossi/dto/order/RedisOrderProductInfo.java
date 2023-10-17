package com.example.bossi.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Redis 저장된 주문상품 정보")
public class RedisOrderProductInfo {
    private Long productId;
    private List<List<Integer>> productOption;
    private List<Integer> productCount;
}
