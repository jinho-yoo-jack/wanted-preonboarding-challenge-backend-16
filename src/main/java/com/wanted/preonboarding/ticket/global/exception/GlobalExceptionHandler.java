package com.wanted.preonboarding.ticket.global.exception;

import ch.qos.logback.core.util.ContextUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wanted.preonboarding.ticket.global.dto.BaseResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.BindException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    private static final String field = "${field}";

    @ExceptionHandler(com.wanted.preonboarding.ticket.global.exception.ServiceException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(com.wanted.preonboarding.ticket.global.exception.ServiceException e) {
        BaseResDto baseResDto = new BaseResDto();
        baseResDto.setResultCode(e.getResultCode());
        baseResDto.setResultMessage(e.getResultMessage());

        return baseResDto;
    }


    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(BindException e) {
        BaseResDto baseResDto = new BaseResDto();
        FieldError fieldError = e.getBindingResult().getFieldError();

        if (fieldError == null) {
            baseResDto.setResultCode(ResultCode.INTERNAL_ERROR.getResultCode());
            baseResDto.setResultMessage(ResultCode.INTERNAL_ERROR.getResultMessage());

            return baseResDto;
        }

        String code = fieldError.getCode();

        if ("NotNull".equals(code) || "NotEmpty".equals(code) || "NotBlank".equals(code)) {
            baseResDto.setResultCode(ResultCode.VALID_NOT_NULL.getResultCode());
            baseResDto.setResultMessage(ResultCode.VALID_NOT_NULL.getResultMessage().replace(field, fieldError.getField()));
        } else if ("Pattern".equals(code)) {
            baseResDto.setResultCode(ResultCode.VALID_NOT_REGEXP.getResultCode());
            baseResDto.setResultMessage(ResultCode.VALID_NOT_REGEXP.getResultMessage().replace(field, fieldError.getField()));
        } else if ("MaxByte".equals(code)) {
            baseResDto.setResultCode(ResultCode.PARAM_NOT_VALID.getResultCode());
            baseResDto.setResultMessage(String.format("%s 값이 %dbyte 보다 큽니다.", fieldError.getRejectedValue(), fieldError.getArguments()[1]));
        } else {
            baseResDto.setResultCode(ResultCode.PARAM_NOT_VALID.getResultCode());
            baseResDto.setResultMessage(ResultCode.PARAM_NOT_VALID.getResultMessage());
        }

        return baseResDto;
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(MissingServletRequestParameterException e) {
        BaseResDto baseResDto = new BaseResDto();
        baseResDto.setResultCode(ResultCode.VALID_NOT_NULL.getResultCode());
        baseResDto.setResultMessage(ResultCode.VALID_NOT_NULL.getResultMessage().replace(field, e.getParameterName()));

        return baseResDto;
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(Exception e) {
        BaseResDto baseResDto = new BaseResDto();
        baseResDto.setResultCode(ResultCode.INTERNAL_ERROR.getResultCode());
        baseResDto.setResultMessage(ResultCode.INTERNAL_ERROR.getResultMessage());

        return baseResDto;
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(UnsupportedEncodingException e) {
        BaseResDto baseResDto = new BaseResDto();
        baseResDto.setResultCode(ResultCode.INTERNAL_ERROR.getResultCode());
        baseResDto.setResultMessage(ResultCode.INTERNAL_ERROR.getResultMessage());

        return baseResDto;
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(NoSuchAlgorithmException e) {
        BaseResDto baseResDto = new BaseResDto();
        baseResDto.setResultCode(ResultCode.INTERNAL_ERROR.getResultCode());
        baseResDto.setResultMessage(ResultCode.INTERNAL_ERROR.getResultMessage());

        return baseResDto;
    }

    @ExceptionHandler(URISyntaxException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(URISyntaxException e) {
        BaseResDto baseResDto = new BaseResDto();
        baseResDto.setResultCode(ResultCode.INTERNAL_ERROR.getResultCode());
        baseResDto.setResultMessage(ResultCode.INTERNAL_ERROR.getResultMessage());

        return baseResDto;
    }

    @ExceptionHandler(InvalidKeyException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(InvalidKeyException e) {
        BaseResDto baseResDto = new BaseResDto();
        baseResDto.setResultCode(ResultCode.INTERNAL_ERROR.getResultCode());
        baseResDto.setResultMessage(ResultCode.INTERNAL_ERROR.getResultMessage());

        return baseResDto;
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(JsonProcessingException e) {
        BaseResDto baseResDto = new BaseResDto();
        baseResDto.setResultCode(ResultCode.INTERNAL_ERROR.getResultCode());
        baseResDto.setResultMessage(ResultCode.INTERNAL_ERROR.getResultMessage());

        return baseResDto;
    }
}
