package com.example.bossi.dto.product.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "장바구니 상품")
public class AddToCartRequest {

    //String email, Long productId, String options, String optionCount
    private String email;
    private Long productId;
    private String options;
    private String optionCount;
}
