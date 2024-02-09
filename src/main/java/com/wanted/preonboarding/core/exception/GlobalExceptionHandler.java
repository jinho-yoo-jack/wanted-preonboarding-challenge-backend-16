package com.wanted.preonboarding.core.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public String entityNotFoundExceptionHandler(Model model){
        log.error("EntityNotFoundException");
        model.addAttribute("errorMessage", "찾으시는 정보가 존재하지 않습니다. 입력값을 다시 확인해주세요.");
        return "error/500";
    }

}
