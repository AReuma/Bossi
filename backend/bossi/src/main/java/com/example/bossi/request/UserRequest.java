package com.example.bossi.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequest {
    private String email;
    private String password;
    private String nickName;
    private int phoneNum;
    private String recommender;
    private boolean select;

    public UserRequest(String email, String password, String nickName, int phoneNum, String recommender, boolean select) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phoneNum = phoneNum;
        this.recommender = recommender;
        this.select = select;
    }
}
