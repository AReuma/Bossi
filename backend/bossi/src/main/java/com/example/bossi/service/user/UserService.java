package com.example.bossi.service.user;

import com.example.bossi.entity.Role;
import com.example.bossi.entity.SocialType;
import com.example.bossi.entity.User;
import com.example.bossi.entity.dto.UserJoinRequest;
import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import com.example.bossi.repository.UserRepository;
import com.example.bossi.response.user.CheckPhoneResponse;
import com.example.bossi.service.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenService jwtTokenService;

    public String join(UserJoinRequest userJoinRequest){

        userRepository.findByEmail(userJoinRequest.getEmail())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, userJoinRequest.getEmail()+"은 이미 존재함.");
                });

        String referralCode = RandomStringUtils.randomAlphanumeric(10);

        // 저장
        User user = User.builder()
                .email(userJoinRequest.getEmail())
                .password(passwordEncoder.encode(userJoinRequest.getPassword()))
                .phoneNum(userJoinRequest.getPhoneNum())
                .nickName(userJoinRequest.getNickName())
                .recommender(userJoinRequest.getRecommender())
                .referralCode(referralCode)
                .role(Role.USER)
                .registerStatus(Boolean.TRUE)
                .socialType(SocialType.GENERAL)
                .checkSMS(userJoinRequest.getCheckSMS())
                .build();

        log.info("join()");

        userRepository.save(user);

        return "SUCCESS";
    }

    public String login(String emil, String password){
        // emil 없음
        User selectedUser = userRepository.findByEmail(emil)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUNT, "아이디가 존재하지않음"));

        // 비밀번호 틀림
        if(!passwordEncoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "잘못된 비밀번호입니다.");
        }

        //return jwt.createToken(selectedUser.getEmail(), key, expireTimeMs);
        return null;
    }


    public Boolean checkId(String email) {
        log.info("===== {} ======", email);
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()){
            return Boolean.TRUE;
        }else return Boolean.FALSE;
    }
}
