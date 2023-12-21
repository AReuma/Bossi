package com.example.bossi.response.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Schema(description = "주문 상품의 정보")
public class OrderProductInfo {

    // 상품 이름
    // 선택된 옵션
    // 가격
    // 배송비
    @Schema(description = "상품 ID")
    private Long productId; // 상품의 고유 ID

    @Schema(description = "상점 이름")
    private String storeName;

    @Schema(description = "상품 이름")
    private String productName;

    @Schema(description = "상품 이미지")
    private String productImg;

    @Schema(description = "옵션")
    private List<String> optionList;

    @Schema(description = "주문 가격")
    private List<Float> totalPrice;

    private Float totalProductPrice;

    @Schema(description = "배송비")
    private Float deliveryCharge;

}
