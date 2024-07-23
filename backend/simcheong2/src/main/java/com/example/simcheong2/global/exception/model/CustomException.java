package com.example.simcheong2.global.exception.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final String detail;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());

        this.status = errorCode.getHttpStatus();
        this.detail = errorCode.getMessage();
    }

    /*
     * 비즈니스 exception 처리 시 추가적인 내용이 필요할 경우
     */
    public CustomException(ErrorCode errorCode, String subject) {
        super(subject + " " + errorCode.getMessage());

        this.status = errorCode.getHttpStatus();
        this.detail = subject + " " + errorCode.getMessage();
    }

    /*
     * field exception 처리 시 사용
     */
    public CustomException(ErrorCode errorCode, Throwable e) {
        super(errorCode.getMessage(), e);

        this.status = errorCode.getHttpStatus();
        this.detail = e.getMessage() + " " + errorCode.getMessage();
    }
}
