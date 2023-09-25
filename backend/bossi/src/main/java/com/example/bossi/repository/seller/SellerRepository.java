package com.example.bossi.repository.seller;

import com.example.bossi.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByEmail(String email);

    Seller findSellerByEmail(String email);
}
