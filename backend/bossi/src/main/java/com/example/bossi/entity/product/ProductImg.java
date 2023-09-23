package com.example.bossi.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_IMG_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private String img;

    public void setProduct(Product product){
        this.product = product;

    }

    public static ProductImg saveProductImg(String img, Product product){
        ProductImg productImg = ProductImg.builder()
                .img(img)
                .build();

        product.addProductImg(productImg);

        return productImg;
    }
}
