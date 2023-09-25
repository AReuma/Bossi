package com.example.bossi.controller.product;

import com.example.bossi.dto.product.cart.DirectBuyProductRequest;
import com.example.bossi.response.product.cart.DirectButOrderItemInfo;
import com.example.bossi.service.product.cart.CartService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "CartController", description = "구매 관련 api 입니다.")
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "바로 구매 상품 리스트", description = "바로 구매 상품 리스트 보여주는 메서드")
    @ApiImplicitParam(name = "phoneNum", value = "전화번호")
    @PostMapping("/directBuy")
    public ResponseEntity<DirectButOrderItemInfo> directBuyProduct(@Valid @RequestBody DirectBuyProductRequest directBuyProductRequest){
        log.info("directBuyProduct");

        return cartService.directBuyOrderList(Long.valueOf(directBuyProductRequest.getProductId()), directBuyProductRequest.getOptions(), directBuyProductRequest.getOptionCount());
    }

    @PutMapping("/option")
    public ResponseEntity<String> optionModify(@RequestBody List<String> options){
        log.info("options: {}", options);
        return cartService.modifyDirectOption(options);
    }
}