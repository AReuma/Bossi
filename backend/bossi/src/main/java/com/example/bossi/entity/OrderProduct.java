package com.example.bossi.entity;

import com.example.bossi.entity.product.Product;
import com.example.bossi.entity.product.ProductDetailOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_PRODUCT")
    private Long OrderProduct;

    private float orderPrice;     // 주문 가격
    private int count; // 주문 수량

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "ORDER_PRODUCT_DETAIL_OTION_ID")
    private OrderProductDetailOption orderProductDetailOption;

    public void setOrder(Order order){
        this.order = order;
    }

    public void setOrderProductDetailOption(OrderProductDetailOption orderProductDetailOption){this.orderProductDetailOption = orderProductDetailOption;}

    public static OrderProduct createOrderProduct(Product product, float orderPrice, int count){
        OrderProduct orderProduct = com.example.bossi.entity.OrderProduct.builder()
                .product(product)
                .orderPrice(orderPrice)
                .count(count)
                .build();

        product.removeStock(count);

        return orderProduct;
    }



}
