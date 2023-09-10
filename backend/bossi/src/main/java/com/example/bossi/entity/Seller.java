package com.example.bossi.entity;

import com.example.bossi.entity.product.Product;
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
public class Seller {

    @Id @GeneratedValue
    @Column(name = "SELLER_ID")
    private Long id;

    @OneToMany(mappedBy = "seller")
    private List<Product> productList = new ArrayList<>();

    public void addProduct(Product product){
        productList.add(product);
        product.setSeller(this);
    }

}
