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
        int status,

        String message,

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<ValidationError> errors,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime serverDateTime
) {

    public static ErrorResponse from(
            int statusCode,
            final Exception exception
    ) {
        return ErrorResponse.builder()
                .status(statusCode)
                .message(exception.getMessage())
                .serverDateTime(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse fromBind(
            final BindException e
    ) {
        return ErrorResponse.builder()
                .status(400)
                .message(e.getMessage())
                .errors(ValidationError.of(e.getBindingResult().getFieldErrors()))
                .serverDateTime(LocalDateTime.now())
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
