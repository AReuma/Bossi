package com.example.bossi.response.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CheckPhoneResponse {
    private final String num;
    private final String socialType;
}
