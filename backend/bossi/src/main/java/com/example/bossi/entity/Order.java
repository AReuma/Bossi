package com.example.bossi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ORDERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @NotBlank
    private Long orderNum;

    private LocalDateTime orderDate;    // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    // 주문 상태

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;  // 주문 회원

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;  // 배송 정보

    private String orderMsg;    // 추가 메모

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();
    //private


    //== 비즈니스 로직 ==//
    /**
     * 주문 취소
     * */

    //== 조회 로직 ==//
    /**
     * 전체 주문 가격 조회
     * */

    /**
     * 주문 배송지 조회
     * */
}
