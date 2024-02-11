package com.wanted.preonboarding.ticket.domain.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum ActiveType {

    OPEN("enable"),

    CLOSE("disable");

    private final String code;

    public static ActiveType fromCode(String code) {
        return Arrays.stream(values())
                .filter(x -> x.getCode().equals(code))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
