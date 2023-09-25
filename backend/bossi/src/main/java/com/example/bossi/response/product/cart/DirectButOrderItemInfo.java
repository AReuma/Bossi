package com.example.bossi.response.product.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class DirectButOrderItemInfo {
    // 작가 이름, 상품이름, 옵션, 선택 갯수, 가격
    private String storeName;
    private final float price;
    private final int rating;
    private final float ratingPrice;
    private String productName;
    private List<Integer> optionCount;
    private float optionTotalPrice;
    private String productImg;
    private List<List<Integer>> option;
    private List<String> optionStr; // 옵션 문자열
    private List<Float> optionPrice; // 옵션당 가격
    private float deliveryCharge;
    private float freeDeliverTotalCharge;
    private int stockQuantity;
    private final List<Map<String, Object>> productOption;
}
