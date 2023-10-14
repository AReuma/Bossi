package com.example.bossi.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "주문 정보")
public class CompleteOrderRequest {

    // 주문 고객 이름, 전화번호
    // 기존 배송지일 경우/ 신규 배송지일 경우 배송지 db에 저장
    // 적립금, 상품, 옵션, 수량
    // 쿠폰... (나중에 구현)
    // 배송 완료 후 적립금 저장.
    @Schema(description = "상품 아이디")
    private Long productId;

    @Schema(description = "주문 상품 옵션")
    private String options;

    @Schema(description = "주문 상품 옵션 개수")
    private String optionCount;

    @Schema(description = "주문자")
    private String email;
//{productId, options, optionCount, email, receiver, address, detailAddr, zipcode, phoneNum, deliveryName, orderMsg, usePoint}
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
    private boolean isSave;

    @Schema(description = "배송정보 - 기본배송지 설정 여부")
    private boolean isBasic;
}
