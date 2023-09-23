package com.example.bossi.response.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ProductContentResponse {

    private final Long productId;
    private final String category;
    private final String storeName;
    private final String title;
    private final float price;
    private final int rating;
    private final float ratingPrice;
    private final float deliveryCount;
    private final float freeDeliverTotalCharge;
    private final int salesQuantity;
    private final List<Map<String, Object>> productOption;
    private final int stockQuantity;
    private final String content;
    private final List<String> productImg;

}
