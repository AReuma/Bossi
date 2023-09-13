package com.example.bossi.response.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProductContentResponse {

    private String category;
    private String title;
    private int price;
    private int rating;
    private int ratingPrice;
    private int deliveryCount;
    private int freeCount;
    private List<String> options;
    private List<List<String>> detailOption;
    private int stockQuantity;
    private String content;

}
