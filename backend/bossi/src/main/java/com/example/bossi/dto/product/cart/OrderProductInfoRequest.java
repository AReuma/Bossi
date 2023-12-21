package com.example.bossi.dto.product.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "구매 상품 정보")
public class OrderProductInfoRequest {

    @Schema(description = "상품 아이디")
    private String productId;

    @Schema(description = "옵션")
    private String option;

    @Schema(description = "옵션 개수")
    private String optionCount;
}
