package com.example.bossi.controller.product;

import com.example.bossi.dto.seller.CreateContentRequest;
import com.example.bossi.service.seller.SellerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "ManagerController", description = "권한 관리 관련 api 입니다.")
@RequestMapping("/api/v1/product")
public class productController {

    private final SellerService sellerService;

    @GetMapping("/{id}")
    public void content(@PathVariable(name = "id") Long id ){
        log.info("content");

        //return sellerService.showContent(id);
    }
}
