package com.example.bossi.service.payment;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import java.io.IOException;

public interface PaymentService {
    IamportResponse<Payment> getPaymentUserInfo(String impUid) throws IamportResponseException, IOException;
}
