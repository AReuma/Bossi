package com.example.bossi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
public class UserLoginRequest {
    @NotNull
    private String email;

    @NotNull
    private String password;
}
