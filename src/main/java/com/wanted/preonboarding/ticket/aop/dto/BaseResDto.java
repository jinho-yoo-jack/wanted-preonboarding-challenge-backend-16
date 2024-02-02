package com.wanted.preonboarding.ticket.aop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter @Setter
@SuperBuilder
public class BaseResDto {
    private int resultCode;
    private String resultMessage;

    public static BaseResDto of(int resultCode, String resultMessage) {
        return BaseResDto.builder()
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .build();
    }
}
