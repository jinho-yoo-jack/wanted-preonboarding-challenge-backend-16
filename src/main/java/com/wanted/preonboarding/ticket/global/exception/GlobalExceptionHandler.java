package com.wanted.preonboarding.ticket.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wanted.preonboarding.ticket.global.dto.BaseResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestControllerAdvice
@RestController
public class GlobalExceptionHandler {

    private static final String FIELD = "${field}";
    private static final String NOT_NULL = "NotNull";
    private static final String NOT_EMPTY = "NotEmpty";
    private static final String NOT_BLANK = "NotBlank";
    private static final String PATTERN = "Pattern";
    private static final String MAX_BYTE = "MaxByte";
    @ExceptionHandler(com.wanted.preonboarding.ticket.global.exception.ServiceException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(com.wanted.preonboarding.ticket.global.exception.ServiceException e) {
        return BaseResDto.of(e.getResultCode(), e.getResultMessage());
    }


    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(BindException e) {

        FieldError fieldError = e.getBindingResult().getFieldError();

        if (fieldError == null) {
            return BaseResDto.of(ResultCode.INTERNAL_ERROR.getResultCode(), ResultCode.INTERNAL_ERROR.getResultMessage());
        }

        return findBindError(fieldError);
    }

    private static BaseResDto findBindError(FieldError fieldError) {
        String code = fieldError.getCode();

        if (NOT_NULL.equals(code) || NOT_EMPTY.equals(code) || NOT_BLANK.equals(code)) {
            return BaseResDto.of(ResultCode.VALID_NOT_NULL.getResultCode(), ResultCode.VALID_NOT_NULL.getResultMessage().replace(FIELD, fieldError.getField()));
        } else if (PATTERN.equals(code)) {
            return BaseResDto.of(ResultCode.VALID_NOT_REGEXP.getResultCode(), ResultCode.VALID_NOT_REGEXP.getResultMessage().replace(FIELD, fieldError.getField()));
        } else if (MAX_BYTE.equals(code)) {
            return BaseResDto.of(ResultCode.PARAM_NOT_VALID.getResultCode(), String.format("%s 값이 %dbyte 보다 큽니다.", fieldError.getRejectedValue(), fieldError.getArguments()[1]));
        } else {
            return BaseResDto.of(ResultCode.PARAM_NOT_VALID.getResultCode(), ResultCode.PARAM_NOT_VALID.getResultMessage());
        }
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResDto exception(MissingServletRequestParameterException e) {
        return BaseResDto.of(ResultCode.VALID_NOT_NULL.getResultCode(), ResultCode.VALID_NOT_NULL.getResultMessage().replace(FIELD, e.getParameterName()));
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResDto exception(Exception e) {
        return BaseResDto.of(ResultCode.INTERNAL_ERROR.getResultCode(), ResultCode.INTERNAL_ERROR.getResultMessage());
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResDto exception(UnsupportedEncodingException e) {
        return BaseResDto.of(ResultCode.UNSUPPORTED_ENCODING.getResultCode(), ResultCode.UNSUPPORTED_ENCODING.getResultMessage());
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResDto exception(NoSuchAlgorithmException e) {
        return BaseResDto.of(ResultCode.NO_SUCH_ALGORITHM.getResultCode(), ResultCode.NO_SUCH_ALGORITHM.getResultMessage());
    }

    @ExceptionHandler(URISyntaxException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResDto exception(URISyntaxException e) {
        return BaseResDto.of(ResultCode.URI_SYNTAX.getResultCode(), ResultCode.URI_SYNTAX.getResultMessage());
    }

    @ExceptionHandler(InvalidKeyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResDto exception(InvalidKeyException e) {
        return BaseResDto.of(ResultCode.INVALID_KEY.getResultCode(), ResultCode.INVALID_KEY.getResultMessage());
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResDto exception(JsonProcessingException e) {
        return BaseResDto.of(ResultCode.JSON_PROCESSING.getResultCode(), ResultCode.JSON_PROCESSING.getResultMessage());
    }
}
