package com.example.bossi.repository.seller;


import com.example.bossi.entity.product.ProductContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductContentRepository extends JpaRepository<ProductContent, Long> {

    ProductContent findProductContentByProductId(Long id);
}
