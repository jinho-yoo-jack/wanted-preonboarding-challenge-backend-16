package com.wanted.preonboarding.core.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.OK;

/**
 * API 응답에 대한 공통 포맷입니다.
 * @since 1.0
 * @author bombo
 * @param statusCode
 * @param data
 * @param serverTime
 * @param <T>
 */
public record ApiResponse<T>(
        int statusCode,

        T data,

        LocalDateTime serverTime
) {

    /**
     * HTTP 상태코드 OK를 반환하는 템플릿 메서드입니다.
     * @since 1.0
     */
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(OK.value(), data, LocalDateTime.now());
    }
}
