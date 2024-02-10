package com.wanted.preonboarding.ticketing.domain.entity;

import lombok.Getter;

@Getter
public enum AlarmType {
    EMAIL("email"),
    SNS("sns");

    private final String typeName;

    AlarmType(String typeName) {
        this.typeName = typeName;
    }
}
