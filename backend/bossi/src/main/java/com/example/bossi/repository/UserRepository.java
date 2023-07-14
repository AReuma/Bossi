package com.example.bossi.repository;

import com.example.bossi.entity.SocialType;
import com.example.bossi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findBySocialTypeAndEmail(SocialType socialType, String email);
}
