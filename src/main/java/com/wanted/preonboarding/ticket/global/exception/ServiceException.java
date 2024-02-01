package com.wanted.preonboarding.ticket.global.exception;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;


@RequiredArgsConstructor
public class ServiceException extends Exception {
    private final int resultCode;
    private final String resultMessage;

    public ServiceException(@NonNull ResultCode resultCodeEnum) {
        this.resultCode = resultCodeEnum.getResultCode();
        this.resultMessage = resultCodeEnum.getResultMessage();
    }

    public ServiceException(@NonNull ResultCode resultCodeEnum, @NonNull Map<String, Object> params) {
        this.resultCode = resultCodeEnum.getResultCode();
        String messageTemplate = resultCodeEnum.getResultMessage();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            messageTemplate = messageTemplate.replaceAll(String.format("\\$\\{%s\\}", entry.getKey()), String.valueOf(entry.getValue()));
        }

        this.resultMessage = messageTemplate;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
