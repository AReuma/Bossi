package com.example.bossi.entity.product;

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
public class ProductDetailOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_DETAIL_OPTION_ID")
    private Long id;

    // 한개의 옵션에는 여러개의 상세 옵션들이 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_OPTION_ID")
    private ProductOption productOption;

    private String optionValue;

    private float price;

    public static ProductDetailOption createDetailOption(String optionValue, Float price, ProductOption productOption){
        ProductDetailOption productDetailOption = ProductDetailOption.builder()
                .optionValue(optionValue)
                .productOption(productOption)
                .price(price)
                .build();

        productOption.addProductDetailOptionList(productDetailOption);

        return productDetailOption;
    }


    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }
}
