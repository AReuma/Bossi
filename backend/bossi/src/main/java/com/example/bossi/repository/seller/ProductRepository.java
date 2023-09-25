package com.example.bossi.repository.seller;

import com.example.bossi.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findFirst5ByOrderByIdAsc();

}
