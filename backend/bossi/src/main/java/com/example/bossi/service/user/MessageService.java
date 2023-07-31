package com.example.bossi.service.user;

import com.example.bossi.entity.User;
import com.example.bossi.exception.AppException;
import com.example.bossi.exception.ErrorCode;
import com.example.bossi.repository.UserRepository;
import com.example.bossi.response.user.CheckPhoneResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:application-API-KEY.properties")
public class  MessageService {

    private final UserRepository userRepository;

    //@Value("${COOLSMS-API-KEY}")
    private String apiKey  = "NCSS8BOBGTWBOPBY";

    //@Value("{${COOLSMS-API-SECRET}")
    private String apiSecret ="3S1B2CKP2V4MUDYYFTMJRKZY6AGZPWFD";

    @Value("${COOLSMS-OUTGOING_NUM}")
    private String outgoingPhoneNum;

    public CheckPhoneResponse checkPhoneNum(String phoneNum) {
        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");

        Optional<User> findUser = userRepository.findByPhoneNum(phoneNum);

        Message message = new Message();

        String tmpStr = RandomStringUtils.randomNumeric(5);
        String msg = "bossi 인증번호 ["+ tmpStr + "]를 넣어주세요.\n" +
                "주문/배송 시 이 번호로 안내해 드릴게요.^-^";

        message.setFrom(outgoingPhoneNum);
        message.setTo(phoneNum);
        message.setText(msg);
        log.info(tmpStr);
        //messageService.sendOne(new SingleMessageSendingRequest(message));

        return findUser.map(user -> new CheckPhoneResponse(tmpStr, user.getSocialType().name())).orElseGet(() -> new CheckPhoneResponse(tmpStr, "NEW_MEM"));

    }
}
