package com.example.bossi.response.product.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "주문 상품 결제 정보")
public class OrderMultiProductInfo {

    @Schema(description = "주문 고객 이름")
    private String name;

    @Schema(description = "주문 고객 전화번호")
    private String phoneNum;

    @Schema(description = "구매자 보유 포인트")
    private float point;

    @Schema(description = "배송지 유무")
    private boolean deliveryCheck;

    @Schema(description = "배송지 정보")
    private AddrInfo delivery;

    @Schema(description = "주문 상품 정보")
    private List<ProductInfo> orderProduct;
}
