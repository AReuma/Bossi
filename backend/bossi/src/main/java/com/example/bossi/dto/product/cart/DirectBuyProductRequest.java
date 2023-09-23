package com.example.bossi.dto.product.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "바로 구매 상품")
public class DirectBuyProductRequest {

    @NotBlank(message = "상품을 주문하세요")
    @Schema(description = "주문 상품 아이디")
    private String productId;

    @Schema(description = "주문 상품 옵션")
    private String options;

    @Schema(description = "주문 상품 옵션 개수")
    private String optionCount;

}
