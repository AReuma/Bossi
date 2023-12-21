package com.example.bossi.response.product.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "주문자 주소 정보")
public class AddrInfo {

    @Schema(description = "주소")
    private String addr;

    @Schema(description = "우편번호")
    private String zipcode;

    @Schema(description = "배송지명")
    private String addrName;

    @Schema(description = "수령인")
    private String recipient;

    @Schema(description = "수령인 전화번호")
    private String phoneNum;

}
