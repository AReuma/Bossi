package com.example.bossi.controller.seller;

import com.example.bossi.dto.seller.CreateContentRequest;
import com.example.bossi.service.seller.SellerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "SellerController", description = "판매자 관련 api 입니다.")
@RequestMapping("/api/v1/seller/product")
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/create")
    public ResponseEntity<String> createContent(@RequestBody CreateContentRequest contentRequest){
        log.info("createContent");

        return sellerService.createContent(contentRequest);
    }
}
