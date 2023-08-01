package com.example.bossi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, ""),
    USERNAME_NOT_FOUNT(HttpStatus.NOT_FOUND, ""),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, ""),
    INVALID_PHONENUM(HttpStatus.UNAUTHORIZED, ""),
    NULL_REQUEST(HttpStatus.FORBIDDEN, ""),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, ""),
    ;

    private HttpStatus httpsStatus;
    private String message;
}
