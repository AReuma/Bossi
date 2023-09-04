package com.example.bossi.repository.user;

import com.example.bossi.entity.SocialType;
import com.example.bossi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findUserByEmail(String email);

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findByPhoneNum(String phoneNum);

    /*@Modifying
    @Query("update User u set u.refreshToken = :refreshToken where u.email = :email")
    void updateRefreshToken(String email, String refreshToken);*/
}
