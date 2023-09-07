package com.example.bossi.initDB;

import com.example.bossi.entity.*;
import com.example.bossi.repository.manager.WaitingListRepository;
import com.example.bossi.repository.user.UserRepository;
import com.example.bossi.service.manager.ManagerService;
import com.example.bossi.service.user.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;

@Component
@RequiredArgsConstructor
public class UserInitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        private final UserRepository userRepository;
        private final WaitingListRepository waitingListRepository;

        public void dbInit1(){
            User user = createUser(1L, "kuuniin@gmail.com", passwordEncoder.encode("dkfma!123"), "유저1", "010-0114-0114", Boolean.TRUE, SocialType.GENERAL, Role.USER, Boolean.TRUE);
            userRepository.save(user);

            User user1 = createUser(1L, "dkfma@naver.com", passwordEncoder.encode("dkfma!123"), "유저1", "010-1234-1234", Boolean.TRUE, SocialType.GENERAL, Role.USER, Boolean.TRUE);
            userRepository.save(user1);

            User user2 = createUser(2L, "dkfma2@naver.com", passwordEncoder.encode("dkfma!123"), "유저2", "010-4321-4321", Boolean.TRUE, SocialType.GENERAL, Role.USER, Boolean.TRUE);
            userRepository.save(user2);

            User user3 = createUser(3L, "dkfma3@naver.com", passwordEncoder.encode("dkfma!123"), "유저3", "010-9876-9876", Boolean.TRUE, SocialType.GENERAL, Role.USER, Boolean.TRUE);
            userRepository.save(user3);

            User user4 = createUser(4L, "dkfma4@naver.com", passwordEncoder.encode("dkfma!123"), "유저4", "010-8765-8765", Boolean.TRUE, SocialType.GENERAL, Role.USER, Boolean.TRUE);
            userRepository.save(user4);

            User manager = createUser(4L, "manager@naver.com", passwordEncoder.encode("dkfma!123"), "매니저1", "010-5432-5432", Boolean.TRUE, SocialType.GENERAL, Role.ADMIN, Boolean.TRUE);
            userRepository.save(manager);
        }

        public void dbInit2(){
            WaitingList waitingList = createWaitingList(1L, "kuuniin@naver.com", "kuuniin@gmail.com", WaitingListStatus.WAIT);
            waitingListRepository.save(waitingList);

            WaitingList waitingList2 = createWaitingList(2L, "zhzkzhffk35@naver.com", "zhzkzhffk35@gmail.com", WaitingListStatus.WAIT);
            waitingListRepository.save(waitingList2);
        }

        private static WaitingList createWaitingList(Long id, String email, String sendEmail, WaitingListStatus status) {
            WaitingList waitingList = WaitingList.builder()
                    .id(id)
                    .email(email)
                    .sendEmail(sendEmail)
                    .status(status)
                    .build();

            return waitingList;
        }

        private static User createUser(Long id, String email, String password, String nickName, String phoneNum, Boolean checkSMS, SocialType socialType, Role role, Boolean registerStatus) {
            User user = User.builder()
                    .id(id)
                    .email(email)
                    .referralCode("dses3")
                    .password(password)
                    .nickName(nickName)
                    .phoneNum(phoneNum)
                    .checkSMS(checkSMS)
                    .socialType(socialType)
                    .role(role)
                    .point(0)
                    .couponCount(0)
                    .registerStatus(registerStatus)
                    .build();

            return user;
        }



    }
}