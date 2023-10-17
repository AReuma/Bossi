package com.example.bossi.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "주문 정보")
public class CompleteOrderMultiProductRequest {

    //{selectProductId, email, orderUser, orderPhoneNum, deliveryName, orderNum, receiver, phoneNum, address, detailAddr, zipcode, orderMsg, usePoint, isSave, isBasic, totalPrice, deliveryPrice}

    @Schema(description = "상품 아이디")
    private String selectProductId;

    @Schema(description = "주문자")
    private String email;

    @Schema(description = "주문정보 - 이름")
    private String orderUser;

    @Schema(description = "주문정보 - 전화번호")
    private String orderPhoneNum;

    @Schema(description = "배송정보 - 배송지이름")
    private String deliveryName;

    @Schema(description = "상품 주문 번호")
    private String orderNum;

    @Schema(description = "배송정보 - 이름")
    private String receiver;

    @Schema(description = "배송정보 - 전화번호")
    private String phoneNum;

    @Schema(description = "배송정보 - 주소")
    private String address;

    @Schema(description = "배송정보 - 상세주소")
    private String detailAddr;

    @Schema(description = "배송정보 - 우편번호")
    private String zipcode;

    @Schema(description = "배송정보 - 주문 메세지")
    private String orderMsg;

    @Schema(description = "사용 포인트")
    private Integer usePoint;

    @Schema(description = "배송정보 - 배송 저장 여부")
    private Boolean isSave;

    @Schema(description = "배송정보 - 기본배송지 설정 여부")
    private Boolean isBasic;

    @Schema(description = "주문 금액")
    private String totalPrice;

    @Schema(description = "배송비")
    private String totalDeliveryPrice;
}
