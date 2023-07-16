package com.example.bossi.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER"), ADMIN("ROLE_ADMIN"), BOSS("ROLE_SELLER");

    private final String key;
}
