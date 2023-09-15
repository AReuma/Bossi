package com.example.bossi.config.auth;

import com.example.bossi.entity.Seller;
import com.example.bossi.entity.User;
import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import com.example.bossi.repository.seller.SellerRepository;
import com.example.bossi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login")
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 Ioc 되어 있는 loadUserByUsername 함수가 실행
@Service
@Slf4j
@RequiredArgsConstructor
public class SellerDetailsService implements UserDetailsService {

    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("==== loadUserByUsername: 로그인 시도중 ====");
        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNT, "사용자가 없음"));

        return new CustomSellerDetails(seller);
    }
}
