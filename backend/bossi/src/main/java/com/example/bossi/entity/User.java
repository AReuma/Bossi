package com.example.bossi.entity;

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

    private String email;

    private String password;

    private String nickName;

    private String imageUrl;

    private String phoneNum;

    private String recommender;

    private Boolean checkSMS;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken;

    private Integer point;

    private Integer couponCount;

    private String referralCode; // 추천인 발급.

    @Column(nullable = false)
    private Boolean registerStatus;

    public void addCoupon(){

    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}
