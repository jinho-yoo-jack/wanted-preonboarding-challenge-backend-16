package com.wanted.preonboarding.core.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    UNKNOWN_EXCEPTION("20","알 수 없는 오류가 발생하였습니다."),
    NOT_EXIST_ENTITY("21","존재 하지 않는 객체에 접근 하였습니다.");
    private final String code;
    private final String message;
    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
