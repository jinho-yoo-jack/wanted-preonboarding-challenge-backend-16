package com.wanted.preonboarding.core.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorResponse(
        int statusCode,

        String message,

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<ValidationError> errors,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime serverTime
) {

    public static ErrorResponse from(
            int statusCode,
            final Exception exception
    ) {
        return ErrorResponse.builder()
                .statusCode(statusCode)
                .message(exception.getMessage())
                .serverTime(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse fromBind(
            final BindException e
    ) {
        return ErrorResponse.builder()
                .statusCode(400)
                .message(e.getMessage())
                .errors(ValidationError.of(e.getBindingResult().getFieldErrors()))
                .serverTime(LocalDateTime.now())
                .build();
    }

    @Builder
    public record ValidationError(
            String field,
            String message
    ) {
        private static ValidationError from(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }

        private static List<ValidationError> of(final List<FieldError> fieldErrors) {
            return fieldErrors.stream()
                    .map(ValidationError::from)
                    .toList();
        }
    }
}
