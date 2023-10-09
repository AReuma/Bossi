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

    private int orderPrice;     // 주문 가격
    private int count; // 주문 수량

    @ManyToOne
    @JoinColumn
    private Order order;

    @ManyToOne
    @JoinColumn
    private Product product;

    @ManyToOne
    @JoinColumn
    private ProductDetailOption productDetailOption;

}
