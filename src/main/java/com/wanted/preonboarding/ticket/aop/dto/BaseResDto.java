package com.wanted.preonboarding.ticket.aop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter @Setter
@SuperBuilder
public class BaseResDto {
    private int statusCode;
    private String message;

    public static BaseResDto of(int statusCode, String message) {
        return BaseResDto.builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }
}
