package com.example.bossi.initDB;

import com.example.bossi.entity.*;
import com.example.bossi.entity.product.Category;
import com.example.bossi.repository.manager.WaitingListRepository;
import com.example.bossi.repository.seller.CategoryRepository;
import com.example.bossi.repository.seller.SellerRepository;
import com.example.bossi.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SellerInitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        /*initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
        initService.dbInit4();*/
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        private final UserRepository userRepository;
        private final WaitingListRepository waitingListRepository;
        private final CategoryRepository categoryRepository;
        private final SellerRepository sellerRepository;

        public void dbInit1(){

        }

        public void dbInit2(){

        }

        public void dbInit3(){

        }

        public void dbInit4(){

        }
    }
}