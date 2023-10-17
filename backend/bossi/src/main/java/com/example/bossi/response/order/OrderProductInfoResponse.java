package com.example.bossi.response.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "주문 완료 정보")
public class OrderProductInfoResponse {


    @Schema(description = "주문번호")
    private String orderNum;

    @Schema(description = "주소")
    private String address;

    @Schema(description = "상세주소")
    private String detailAddr;

    @Schema(description = "우편번호")
    private String zipcode;

    @Schema(description = "수령인")
    private String recipient;

    @Schema(description = "수령인 전화번호")
    private String phoneNum;

    @Schema(description = "적립 예정 포인트")
    private Integer point;

    @Schema(description = "결제 금액")
    private Float price;

    @Schema(description = "배송비")
    private Float deliveryPrice;

    @Schema(description = "사용 된 포인트")
    private Integer usePoint;

    @Schema(description = "상품 정보")
    private List<OrderProductInfo> orderProductInfoList;


}
