package com.example.bossi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
public class EnteringStoreRequest {

    @NotNull
    public String email;

    @NotNull
    public String sendEmail;
}
