package com.example.bossi.entity;

public enum OrderStatus {
    READY_DELIVERY, // 배송 준비중
    READY_PRODUCT, // 상품 준비중
    COMP, // 배송중
    DONE, // 배송 완료
    PURCHASE_CONFIRMED, // 구매 확정
    CANCEL_PURCHASE, // 구매 취소
    REFUNDED, // 환불 완료
    DELIVERY_PENDING, // 상품 보류
}
