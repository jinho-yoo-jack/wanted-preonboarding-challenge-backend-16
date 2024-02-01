package com.wanted.preonboarding.ticket.domain.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {

    OPEN("enable"),

    CLOSE("disable");

    private final String code;
}
