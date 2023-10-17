package com.example.bossi.response.product.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "주문 상품 결제 정보")
public class OrderProductInfo {

    @Schema(description = "주문 고객 이름")
    private String name;

    @Schema(description = "주문 고객 전화번호")
    private String phoneNum;

    @Schema(description = "배송지 유무")
    private boolean deliveryCheck;

    @Schema(description = "배송지 정보")
    private AddrInfo delivery;

    @Schema(description = "상품 판매자")
    private String storeName;

    @Schema(description = "상품 이름")
    private String productTitle;

    @Schema(description = "상품 대표 사진")
    private String productImg;

    /*@Schema(description = "상품 옵션 개수")
    private List<Integer> optionCount;*/

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

    @Schema(description = "구매자 보유 포인트")
    private float point;

    @Schema(description = "예상 포인트")
    private float expectPoint;
}
