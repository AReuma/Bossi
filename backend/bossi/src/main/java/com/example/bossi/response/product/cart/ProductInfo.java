package com.example.bossi.response.product.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
@Schema(description = "주문 상품 정보")
public class ProductInfo {

    @Schema(description = "상품 아이디")
    private Long productId;

    @Schema(description = "상품 판매자")
    private String storeName;

    @Schema(description = "상품 이름")
    private String productTitle;

    @Schema(description = "상품 대표 사진")
    private String productImg;

    @Schema(description = "상품 주문 개수")
    private List<Integer> orderCount;

    @Schema(description = "상품 전체 가격")
    private Float totalProductPrice;

    @Schema(description = "배송비 포함 전체 가격")
    private Float totalPrice;

    @Schema(description = "배송비")
    private Float deliveryPrice;

    private List<List<String>> optionInfo;
    private List<Float> optionPrice;

    @Schema(description = "예상 포인트")
    private float expectPoint;
}
