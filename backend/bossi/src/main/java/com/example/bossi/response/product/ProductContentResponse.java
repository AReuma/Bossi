package com.example.bossi.response.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductContentResponse {

    private String category;
    private String title;
    private int price;
    private int stockQuantity;
    private int ratingCount;
    private int ratingSum;
    private int deliveryCount;
}
