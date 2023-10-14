package com.example.bossi.entity;

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
public class OrderProductDetailOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_PRODUCT_DETAIL_OTION_ID")
    private Long orderProductOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_DETAIL_OTION_ID")
    private ProductDetailOption productDetailOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_PRODUCT")
    private OrderProduct orderProduct;

    public static OrderProductDetailOption createOrderProductDetailOption(ProductDetailOption productDetailOption, OrderProduct orderProduct){
        OrderProductDetailOption orderProductDetailOption = OrderProductDetailOption.builder()
                .productDetailOption(productDetailOption)
                .orderProduct(orderProduct)
                .build();

        orderProduct.setOrderProductDetailOption(orderProductDetailOption);

        return orderProductDetailOption;
    }
}
