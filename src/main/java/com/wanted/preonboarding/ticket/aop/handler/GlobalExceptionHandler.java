package com.wanted.preonboarding.ticket.aop.handler;

import com.wanted.preonboarding.ticket.aop.exception.ServiceException;
import com.wanted.preonboarding.ticket.aop.dto.BaseResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<BaseResDto> exception(ServiceException e) {
        HttpStatus status = HttpStatus.resolve(e.getStatusCode());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        BaseResDto response = BaseResDto.of(e.getStatusCode(), e.getMessage());
        return new ResponseEntity<>(response, status);
    }
}
