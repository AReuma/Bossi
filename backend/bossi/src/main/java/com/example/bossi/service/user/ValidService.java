package com.example.bossi.service.user;

import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidService {

    public void validEmailCheck(String email){
        String emailPattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        if(!Pattern.matches(emailPattern, email)) {
            log.error("이메일 형식이 잘못됨");
            throw new AppException(ErrorCode.BAD_REQUEST, "이메일 형식이 잘못됨.");
        }
    }

    public void validPwCheck(String pw){
        String pwPattern = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}";

        if(!Pattern.matches(pwPattern, pw)) throw new AppException(ErrorCode.BAD_REQUEST, "비밀번호 형식이 잘못됨.");
    }

    public StringBuffer validPhoneCheck(String phone){
        String pattern = "^\\d{3}\\d{3,4}\\d{4}";
        StringBuffer sb;

        //010 12341234
        if(!Pattern.matches(pattern, phone)) throw new AppException(ErrorCode.BAD_REQUEST, "전화번호 형식이 잘못됨");
        else {
            sb = new StringBuffer(phone);
            sb.insert(3, "-");
            if(sb.length() == 11) sb.insert(7, "-");
            else sb.insert(8, "-");

            return sb;
        }
    }
}
