package com.example.bossi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_DUPLICATED(HttpStatus.CONFLICT, ""), // 409
    USER_NOT_FOUNT(HttpStatus.UNAUTHORIZED, ""), // 401
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, ""), // 401
    INVALID_PHONENUM(HttpStatus.UNAUTHORIZED, ""), // 401
    NULL_REQUEST(HttpStatus.BAD_REQUEST, ""), // 400
    BAD_REQUEST(HttpStatus.BAD_REQUEST, ""), // 400
    FORBIDDEN(HttpStatus.FORBIDDEN, ""), // 403
    NOT_ENOUGH_STOCK(HttpStatus.CONFLICT, ""),  // 400
    NOT_ENOUGH_POINT(HttpStatus.CONFLICT, ""),  // 400
    HAVE_NOT_DELIVERY(HttpStatus.CONFLICT, ""),  // 400
    ;

    private HttpStatus httpsStatus;
    private String message;
}
