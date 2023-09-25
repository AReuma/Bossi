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
public class ProductContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_CONTENT_ID")
    private Long id;

    @Lob
    @Column(columnDefinition="MEDIUMTEXT")
    private String content;

    @OneToOne(mappedBy = "productContent", fetch = FetchType.LAZY)
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }
}
