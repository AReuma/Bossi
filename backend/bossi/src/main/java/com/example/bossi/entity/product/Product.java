package com.example.bossi.entity.product;

import com.example.bossi.entity.Seller;
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
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    // 한명의 판매자는 여러개의 제품을 팔 수 있다.
    // 한개의 제품은 한명의 판매자가 만든거다.
    @JoinColumn(name = "SELLER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Seller seller;

    // 한개의 카테고리에는 여러개의 제품이 들어갈 수 있다.
    // 한개의 제품은 한개의 카테고리만 갖을 수 있다.
    @JoinColumn(name = "CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    // 한개의 제품에는 여러개의 옵션이 있다.
    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOption> productOptionList = new ArrayList<>();

    // 한개의 작품에는 여러개의 작품 설명 이미지가 있을 수 있다.
    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductContentImg> productContentImgList = new ArrayList<>();

    // 한개의 제품에는 여러개의 피드백이 있다.
    @OneToMany(mappedBy = "product")
    private List<Feedback> feedbacks = new ArrayList<>();

    // 한개의 작품에는 한개의 설명 내용이 있다.
    @JoinColumn(name = "PRODUCT_CONTENT_ID")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProductContent productContent;

    private String name;    // 상품 이름
    private float price;      // 상품 가격
    private int stockQuantity;      // 개수
    private int salesQuantity;      // 판매 개수

    private int ratingCont;     // 할인율
    private float ratingSum;      // 할인된 최종 금액

    private float deliveryCharge;     // 배송비

    public void setSeller(Seller sellers){
        this.seller = sellers;
    }

    public void setProductContent(ProductContent content) {
        this.productContent = content;
        content.setProduct(this);
    }

    // === 생성 메서드  ===//
    public static Product createProduct(Seller seller, Category category, ProductContent productContent, String name, float price, int stockQuantity, int ratingCont, float ratingSum, float deliveryCharge){
        Product product = Product.builder()
                .seller(seller)
                .category(category)
                .productContent(productContent)
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .ratingCont(ratingCont)
                .ratingSum(ratingSum)
                .productOptionList(new ArrayList<>())
                .deliveryCharge(deliveryCharge)
                .salesQuantity(0)
                .build();

        product.setProductContent(productContent);

        return product;
    }


    public void addProductOptionList(ProductOption productOption){
        productOptionList.add(productOption);
        productOption.setProduct(this);
    }
    public void addFeedback(Feedback feedback){
        feedbacks.add(feedback);
        feedback.setProduct(this);
    }

    public void addProductContentImg(ProductContentImg productContentImg) {
        productContentImgList.add(productContentImg);
        productContentImg.setProduct(this);
    }
}
