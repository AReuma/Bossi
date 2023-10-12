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
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_DETAIL_OPTION_ID")
    private ProductDetailOption productDetailOption;

}
