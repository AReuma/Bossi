package com.example.bossi.entity.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter @Builder
public class UserJoinRequest {
    //email, password, nickName, phoneNum, recommender, select

    @NotNull
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;

    @NotNull
    private String nickName;

    @NotNull
    private String phoneNum;

    @NotNull
    private String recommender;

    @NotNull
    private Boolean checkSMS;

}
