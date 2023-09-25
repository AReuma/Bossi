package com.example.bossi.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_OPTION_ID")
    private Long id;

    // 한개의 상품에는 여러개의 옵션이 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @JsonIgnore
    @OneToMany(mappedBy = "productOption", cascade = CascadeType.ALL)
    private List<ProductDetailOption> productDetailOptionList = new ArrayList<>();

    private String optionsName;

    public void setProduct(Product product) {
        this.product = product;
    }


    public void addProductDetailOptionList(ProductDetailOption productDetailOption){
        this.productDetailOptionList.add(productDetailOption);
        productDetailOption.setProductOption(this);
    }

    public static ProductOption createProductOption(String optionsName, Product product){
        ProductOption productOption = ProductOption.builder()
                .optionsName(optionsName)
                .product(product)
                .productDetailOptionList(new ArrayList<>())
                .build();

        product.addProductOptionList(productOption);

        return productOption;
    }

}
