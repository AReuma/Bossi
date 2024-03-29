package com.example.bossi.service.user;

import com.example.bossi.entity.*;
import com.example.bossi.dto.EnteringStoreRequest;
import com.example.bossi.dto.UserJoinRequest;
import com.example.bossi.dto.UserLoginRequest;
import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import com.example.bossi.repository.seller.SellerRepository;
import com.example.bossi.repository.user.UserRepository;
import com.example.bossi.repository.manager.WaitingListRepository;
import com.example.bossi.response.user.FindIdPwResponseDto;
import com.example.bossi.service.email.EmailSenderService;
import com.example.bossi.service.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    
    private final SellerRepository sellerRepository;

    private final PasswordEncoder passwordEncoder;

    private final ValidService validService;

    private final WaitingListRepository waitingListRepository;

    private final EmailSenderService emailSenderService;

    private final JwtTokenService jwtTokenService;

    public ResponseEntity<String> join(UserJoinRequest dto){
        // userJoinRequest -> null 체크, pw 길이 체크
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USER_DUPLICATED, dto.getEmail()+"은 이미 존재함.");
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
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNT, "아이디가 존재하지않음"));

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
        Optional<Seller> seller = sellerRepository.findByEmail(email);

        if(user.isEmpty() && seller.isEmpty()){
            return Boolean.TRUE;
        }else return Boolean.FALSE;
    }


    public ResponseEntity<FindIdPwResponseDto> findIdPwByPhone(String phoneNum) {
        if(phoneNum.isEmpty()) throw new AppException(ErrorCode.BAD_REQUEST, "전화번호값이 존재하지 않음");

        phoneNum = validService.validPhoneCheck(phoneNum).toString();

        Optional<User> findUser = userRepository.findByPhoneNum(phoneNum);

        if(findUser.isPresent()){
            String tmpStr = RandomStringUtils.randomNumeric(5);
            String email = findUser.get().getEmail();
            /*
            dk**@naver.com 이런식으로 나오도록 구현
            int endIndex = email.indexOf('@');

            String domain = email.substring(endIndex);
            email = email.substring(0, endIndex);

            String tmpEmail = email.substring(2, endIndex);

            email = email.replaceAll("["+tmpEmail+"]", "*") + domain;*/
            return ResponseEntity.ok().body(new FindIdPwResponseDto(tmpStr, email, findUser.get().getSocialType().name()));
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> changePw(UserLoginRequest dto) {
        //if(dto.getEmail().isEmpty()) throw new AppException(ErrorCode.BAD_REQUEST, "전화번호값이 존재하지 않음"); @Valid 로 체크함

        validService.validEmailCheck(dto.getEmail());

        User findUser = userRepository.findUserByEmail(dto.getEmail());

        /*User encryptedUser = findUser.toBuilder()
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();*/

        findUser.changePassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.saveAndFlush(findUser);

        return ResponseEntity.ok().body("비밀번호 변경 성공");
    }

    public ResponseEntity<String> enteringStore(EnteringStoreRequest dto) {

        // waitingList 안에 아이디 중복 확인
        waitingListRepository.findByEmail(dto.getEmail())
                .ifPresent(e -> {
                    throw new AppException(ErrorCode.USER_DUPLICATED, "이미 신청했습니다.");
                });

        // 보낼 메일 유효성 체크
        validService.validEmailCheck(dto.sendEmail);

        // 메일 보내기
        emailSenderService.sendEmailWithAttachment(dto.sendEmail, "123");

        // 신청자 리스트에 저장하기
        WaitingList waitingUser = WaitingList.builder()
                .email(dto.getEmail())
                .sendEmail(dto.sendEmail)
                .status(WaitingListStatus.WAIT)
                .build();

        waitingListRepository.save(waitingUser);

        return ResponseEntity.ok().body("입점 대기 성공");
    }
}
