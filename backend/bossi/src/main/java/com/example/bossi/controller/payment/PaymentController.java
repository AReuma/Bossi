package com.example.bossi.controller.payment;

import com.example.bossi.dto.order.CompleteOrderRequest;
import com.example.bossi.response.order.OrderProductInfoResponse;
import com.example.bossi.service.order.OrderService;
import com.example.bossi.service.order.RedisOrderService;
import com.example.bossi.service.payment.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController{

    private final PaymentService paymentService;
    private final OrderService orderService;
    private final RedisOrderService redisOrderService;

    @PostMapping("/verify/{imp_uid}")
    public IamportResponse<Payment> verifyIamportPOST(@PathVariable(value = "imp_uid") String imp_uid) throws IamportResponseException, IOException {
        log.info("verifyImaportPost: {}", imp_uid);
        return paymentService.getPaymentUserInfo(imp_uid);
    }

    @PostMapping("/order/complete")
    public void orderComplete(@RequestBody CompleteOrderRequest completeOrderRequest){
        log.info("orderComplete: {}",completeOrderRequest.getEmail());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(completeOrderRequest);
            System.out.println("============");
            System.out.println(json);
            System.out.println("============");
        } catch (Exception e) {
            e.printStackTrace();
        }
        orderService.orderComplete(completeOrderRequest);
    }

    @PostMapping("/order/multi/complete")
    public void orderCompleteMultiProduct(@RequestBody CompleteOrderRequest completeOrderRequest){
        log.info("orderComplete");

        orderService.orderComplete(completeOrderRequest);
    }

    @PostMapping("/order/complete/showOrderInfo")
    public ResponseEntity<OrderProductInfoResponse> showOrderComplete(@RequestBody Map<String, String> orderNum){
        log.info("getOrderCompleteNum: "+ orderNum.get("orderNum"));
        return orderService.showOrderComplete(orderNum.get("orderNum"));
    }
}
