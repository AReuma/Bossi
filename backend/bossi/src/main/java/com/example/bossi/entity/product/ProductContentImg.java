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
public class ProductContentImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_CONTENT_IMG")
    private Long id;

    private String imgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public static ProductContentImg createProductContentImg(Product product, String imgName){
        ProductContentImg productContentImg = ProductContentImg.builder()
                .imgName(imgName)
                .product(product)
                .build();

        product.addProductContentImg(productContentImg);

        return productContentImg;
    }

    public void setProduct(Product product){
        this.product = product;
    }
}
