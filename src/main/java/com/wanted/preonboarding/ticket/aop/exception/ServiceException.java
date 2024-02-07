package com.wanted.preonboarding.ticket.aop.exception;

import com.wanted.preonboarding.ticket.aop.StatusCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;


@RequiredArgsConstructor
public class ServiceException extends RuntimeException {
    private final int statusCode;
    private final String message;

    public ServiceException(@NonNull StatusCode statusCode) {
        this.statusCode = statusCode.getStatusCode();
        this.message = statusCode.getMessage();
    }

    public ServiceException(@NonNull StatusCode statusCode, @NonNull Map<String, Object> params) {
        this.statusCode = statusCode.getStatusCode();
        String messageTemplate = statusCode.getMessage();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            messageTemplate = messageTemplate.replaceAll(String.format("\\$\\{%s\\}", entry.getKey()), String.valueOf(entry.getValue()));
        }

        this.message = messageTemplate;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
