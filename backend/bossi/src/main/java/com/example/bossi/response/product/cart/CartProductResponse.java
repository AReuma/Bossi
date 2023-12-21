package com.example.bossi.response.product.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@Schema(description = "장바구니 상품 리스트")
public class CartProductResponse {

    private String productId;
    private String storeName;
    private String productName;
    private List<Float> optionTotalPrice;
    private String productImg;
    private List<List<Integer>> option;
    private List<Integer> optionCount;
    private List<List<String>> optionStr; // 옵션 문자열
    private Float deliveryCharge;
    private Float freeDeliverTotalCharge;
    private Float totalPrice;
    private Float totalPricePlusDelivery;
    private String stockQuantity;
    private Float productPrice;

}
