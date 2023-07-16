package com.example.bossi.config.jwt;

public interface JwtProperties {
    String SECRET = "secret";
    int EXPIRATION_TIME_ACCESS = 60 * 60 * 1000 * 3;
    int EXPIRATION_TIME_REFRESH = 60 * 60 * 1000 * 5;
    String TOKEN_PREFIX = "Bearer ";

    String ACCESS_HEADER = "Authorization";
    String REFRESH_HEADER = "Authorization-refresh";

}
