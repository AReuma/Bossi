package com.example.bossi.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    //email, password, nickName, phoneNum, recommender, select
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @NotBlank
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    //@Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "비밀번호는 영문과 특수문자를 포함하며 8자 이상이어야 합니다.")
    private String password;

    @NotBlank
    private String nickName;

    private String imageUrl;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 형식이 아닙니다. 01x-xxx(x)-xxxx")
    private String phoneNum;

    @NotNull
    private Boolean checkSMS;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken;

    @Builder.Default
    private Integer point = 0;

    @Builder.Default
    private Integer couponCount = 0;

    @NotBlank
    private String referralCode; // 추천인 발급.

    @Column(nullable = false)
    private Boolean registerStatus;

    public void addCoupon(){

    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}
