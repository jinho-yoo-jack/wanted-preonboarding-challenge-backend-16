package com.wanted.preonboarding.ticket.global.common;

public enum ReserveStatus {

    ENABLE(Value.ENABLE),
    DISABLE(Value.DISABLE),
    CANCEL(Value.CANCEL);

    private final String value;

    ReserveStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public interface Value {
        String ENABLE = "enable";
        String DISABLE = "disable";
        String CANCEL = "cancel";
    }
}
