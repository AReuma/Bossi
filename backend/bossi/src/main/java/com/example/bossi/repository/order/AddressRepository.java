package com.example.bossi.repository.order;

import com.example.bossi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findAddressByUserEmailAndBasic(String email, boolean isBasic);
}
