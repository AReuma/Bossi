package com.example.bossi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinRequest {
    //email, password, nickName, phoneNum, recommender, select

    private String email;
    private String password;
    private String nickName;
    private String phoneNum;
    private String recommender;
    private Boolean checkSMS;

}
