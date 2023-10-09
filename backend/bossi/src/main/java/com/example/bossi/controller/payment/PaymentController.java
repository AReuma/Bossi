package com.example.bossi.controller.payment;

import com.example.bossi.dto.order.CompleteOrderRequest;
import com.example.bossi.service.order.OrderService;
import com.example.bossi.service.payment.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController{

    private final PaymentService paymentService;
    private final OrderService orderService;

    @PostMapping("/verify/{imp_uid}")
    public IamportResponse<Payment> verifyIamportPOST(@PathVariable(value = "imp_uid") String imp_uid) throws IamportResponseException, IOException {
        log.info("verifyImaportPost: {}", imp_uid);
        return paymentService.getPaymentUserInfo(imp_uid);
    }

    @PostMapping("/order/complete")
    public void orderComplete(@RequestBody CompleteOrderRequest completeOrderRequest){
        log.info("orderComplete");
        orderService.orderComplete(completeOrderRequest);
    }
}
