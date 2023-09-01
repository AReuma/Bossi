package com.example.bossi.response.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CheckPhoneResponseDto {

    @ApiModelProperty(example = "인증코드")
    private final String num;

    @ApiModelProperty(example = "회원가입한 소셜타입")
    private final String socialType;
}
