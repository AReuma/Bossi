package com.example.bossi.controller.product;

import com.example.bossi.dto.product.cart.AddToCartRequest;
import com.example.bossi.dto.product.cart.DirectBuyProductRequest;
import com.example.bossi.dto.product.cart.OrderProductInfoRequest;
import com.example.bossi.response.product.cart.CartProductResponse;
import com.example.bossi.response.product.cart.DirectButOrderItemInfo;
import com.example.bossi.response.product.cart.OrderMultiProductInfo;
import com.example.bossi.response.product.cart.OrderProductInfo;
import com.example.bossi.service.product.cart.CartService;
import com.example.bossi.service.product.cart.RedisCartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "CartController", description = "구매 관련 api 입니다.")
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final RedisCartService redisCartService;

    @Operation(summary = "바로 구매 상품 리스트", description = "바로 구매 상품 리스트 보여주는 메서드")
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

    @Operation(summary = "주문하기 메서드", description = "옵션을 선택하고 주문 결제할때 정보를 제공하는 메서드")
    @PostMapping("/order")
    public ResponseEntity<OrderProductInfo> orderProduct(@Valid @RequestBody DirectBuyProductRequest dto){
        log.info("orderProduct: {}", dto.getProductId());
        return cartService.orderProduct(Long.valueOf(dto.getProductId()), dto.getOptions(), dto.getOptionCount(), dto.getEmail());
    }

    @PostMapping("/addCart")
    public void addCart(@RequestBody AddToCartRequest dto){
        log.info("addCart{}", dto.getProductId());
        log.info("addCart{}", dto.getOptions());
        redisCartService.addToCart(dto.getEmail(), dto.getProductId(), dto.getOptions(), dto.getOptionCount());
    }

    @PostMapping("/cartCount")
    public Integer checkCartCount(@RequestBody Map<String, String> email){
        log.info("checkCartCount: {}", email);
        return redisCartService.checkCartCount(email.get("email"));
    }

    @PostMapping("/showCart")
    public List<CartProductResponse>  showCart(@RequestBody Map<String, String> email) throws JsonProcessingException {
        log.info("showCart: {}", email);
        return redisCartService.showCartProduct(email.get("email"));
    }

    @PostMapping("/multi/order/{email}")
    public ResponseEntity<OrderMultiProductInfo> orderMultiProduct(@Valid @RequestBody List<OrderProductInfoRequest> orderData, @PathVariable String email){
        log.info("orderMulti {}, {}", email, orderData.get(0).getProductId());

        return cartService.multiOrderProduct(email, orderData);
    }
}
