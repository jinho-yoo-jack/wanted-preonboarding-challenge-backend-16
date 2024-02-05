package com.wanted.preonboarding.ticket.domain.dto;

public enum IsReserveType {
    ENABLE("enable", 1),
    DISABLE("disable", 0);

    private final String text;
    private final int code;

    IsReserveType(String text, int code) {
        this.text = text;
        this.code = code;
    }

    public String getText() {
        return this.text;
    }

    public int getCode() {
        return this.code;
    }
}
