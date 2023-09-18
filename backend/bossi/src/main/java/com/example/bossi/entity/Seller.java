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

    private String email;

    private String password;

    private Role role;

    private String storeName;

    private String bio;

    @Lob
    private String storeIntroduction;

    private String profileImg;

    @OneToMany(mappedBy = "seller")
    private List<Product> productList = new ArrayList<>();

    public void addProduct(Product product){
        productList.add(product);
        product.setSeller(this);
    }

    public static Seller createSeller(String email, String password, String storeName, String bio, String profileImg, String storeIntroduction){
        Seller seller = Seller.builder()
                .email(email)
                .password(password)
                .storeName(storeName)
                .bio(bio)
                .profileImg(profileImg)
                .storeIntroduction(storeIntroduction)
                .role(Role.BOSS)
                .build();

        return seller;
    }

}
