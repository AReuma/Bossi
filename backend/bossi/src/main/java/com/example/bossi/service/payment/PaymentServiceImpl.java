package com.example.bossi.service.payment;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${IAMPORT-API-KEY}")
    private String apiKey;

    @Value("${IAMPORT-SECRET-KEY}")
    private String secretKey;

    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey); // @Value로 주입된 apiKey와 secretKey 사용
    }

    @Override
    public IamportResponse<Payment> getPaymentUserInfo(String imp_uid) throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(imp_uid); //iamport 서비스에 저장된 결제 정보를 가져와 전달함;
    }
}
