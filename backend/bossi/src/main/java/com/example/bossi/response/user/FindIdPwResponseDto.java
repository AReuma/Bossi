package com.example.bossi.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter //@Schema(description = "아이디 비밀번호 찾기 전화번호 인증 응답 DTO")
public class FindIdPwResponseDto {

    @Schema(description = "인증코드")
    private final String num;

    @Schema(description = "아이디")
    private final String email;

    @Schema(description = "회원가입한 소셜타입")
    private final String socialType;
}
