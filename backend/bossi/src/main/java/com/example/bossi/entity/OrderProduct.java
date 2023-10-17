package com.example.bossi.entity;

import com.example.bossi.entity.product.Product;
import com.example.bossi.entity.product.ProductDetailOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_PRODUCT")
    private Long OrderProduct;

    private float deliveryCharge;   // 배송비
    private float orderPrice;     // 주문 가격
    private int count; // 주문 수량

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL)
    private List<OrderProductDetailOption> orderProductDetailOptions = new ArrayList<>();

    public void setOrder(Order order){
        this.order = order;
    }

    public void addOrderProductDetailOption(OrderProductDetailOption orderProductDetailOption){
        orderProductDetailOptions.add(orderProductDetailOption);
    }

    public static OrderProduct createOrderProduct(Product product, float orderPrice, int count, float deliveryCharge){
        OrderProduct orderProduct = com.example.bossi.entity.OrderProduct.builder()
                .product(product)
                .orderPrice(orderPrice)
                .count(count)
                .deliveryCharge(deliveryCharge)
                .orderProductDetailOptions(new ArrayList<>())
                .build();

        product.removeStock(count);

        return orderProduct;
    }



}
