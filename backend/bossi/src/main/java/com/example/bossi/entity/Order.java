package com.example.bossi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    private String orderNum;

    private LocalDateTime orderDate;    // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    // 주문 상태

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;  // 주문 회원

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;  // 배송 정보

    private String orderMsg;    // 추가 메모

    private Float totalPrice;   // 상품 전체 가격

    private Integer usePoint; // 사용한 포인트

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public static Order createOrder(User user, Delivery delivery, List<OrderProduct> orderProducts, String orderNum, float totalPrice, Integer userPoint){
        Order order = Order.builder()
                .user(user)
                .orderNum(orderNum)
                .orderStatus(OrderStatus.READY_PRODUCT)
                .totalPrice(totalPrice)
                .usePoint(userPoint)
                .orderDate(LocalDateTime.now())
                .build();

        order.setDelivery(delivery);
        for (OrderProduct orderProduct : orderProducts) {
            order.addOrderProduct(orderProduct);
        }


        return order;
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderProduct(OrderProduct orderProduct){
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

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
