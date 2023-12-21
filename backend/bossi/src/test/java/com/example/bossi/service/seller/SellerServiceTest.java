package com.example.bossi.service.seller;

import com.example.bossi.dto.seller.JoinSellerRequest;
import com.example.bossi.entity.*;
import com.example.bossi.exception.AppException;
import com.example.bossi.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerService sellerService;

    @Test(expected = AppException.class)
    @DisplayName("입점 신청 승인이 된 사람만 가입가능하다")
    public void 입점신청승인된_사람만_가입가능() throws Exception {
        //given
        User user = createUser(1L, "kuuniin@gmail.com", passwordEncoder.encode("dkfma!123"), "유저1", "010-0114-0114", Boolean.TRUE, SocialType.GENERAL, Role.USER, Boolean.TRUE);
        userRepository.save(user);

        String sendEmail = "kuuniin@gmail.com";

        WaitingList waitingList = WaitingList.builder() // 승인 된 사람
                .email("kuuniin@gmail.com")
                .sendEmail(sendEmail)
                .status(WaitingListStatus.ALLOW)
                .build();


        //when
        JoinSellerRequest request = new JoinSellerRequest("abd@gmail.com", "dkfma123@gmail.com", "password", "storeName", "storeBio", "storeIntroduction", null);
        sellerService.registerSeller(request);

        //then
        fail("seller로 가입한 사용자와 입점 심사를 통과한 사람이 아니면 가입하지 못한다.  ");
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