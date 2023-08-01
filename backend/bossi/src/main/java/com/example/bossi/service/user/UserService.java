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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ValidService validService;

    private final JwtTokenService jwtTokenService;

    public ResponseEntity<String> join(UserJoinRequest dto){
        // userJoinRequest -> null 체크, pw 길이 체크
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, dto.getEmail()+"은 이미 존재함.");
                });

        //전화번호 패턴/길이체크
        StringBuffer sb = validService.validPhoneCheck(dto.getPhoneNum());
        /*String pattern = "^\\d{3}\\d{3,4}\\d{4}";
        StringBuffer sb;

        //010 12341234
        if(!Pattern.matches(pattern, dto.getPhoneNum())) throw new AppException(ErrorCode.BAD_REQUEST, "전화번호 형식이 잘못됨");
        else {
            sb = new StringBuffer(dto.getPhoneNum());
            sb.insert(3, "-");
            if(sb.length() == 11) sb.insert(7, "-");
            else sb.insert(8, "-");

            System.out.println(sb);
        }*/

        //이메일 패턴 체크 (id)
        validService.validEmailCheck(dto.getEmail());
        /*String emailPattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        if(!Pattern.matches(emailPattern, dto.getEmail())) throw new AppExcep|tion(ErrorCode.BAD_REQUEST, "이메일 형식이 잘못됨.");*/

        // 비밀번호 패턴 체크
        validService.validPwCheck(dto.getPassword());
        /*String pwPattern = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}";

        if(!Pattern.matches(pwPattern, dto.getPassword())) throw new AppException(ErrorCode.BAD_REQUEST, "비밀번호 형식이 잘못됨.");*/

        String referralCode = RandomStringUtils.randomAlphanumeric(10);

        // 저장
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phoneNum(sb.toString())
                .nickName(dto.getNickName())
                //.recommender(dto.getRecommender()) 추천인이 있을경우 적립금 + 500;
                // 코드로 누구인지 찾아서 둘다 + 500
                .referralCode(referralCode)
                .role(Role.USER)
                .registerStatus(Boolean.TRUE)
                .socialType(SocialType.GENERAL)
                .checkSMS(dto.getCheckSMS())
                .build();

        log.info("join()");

        userRepository.save(user);

        return ResponseEntity.ok().body("회원가입 성공");
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
        if(email.isEmpty()) throw new AppException(ErrorCode.BAD_REQUEST, "이메일값이 존재하지 않음");

        validService.validEmailCheck(email);

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()){
            return Boolean.FALSE;
        }else return Boolean.TRUE;
    }


}
