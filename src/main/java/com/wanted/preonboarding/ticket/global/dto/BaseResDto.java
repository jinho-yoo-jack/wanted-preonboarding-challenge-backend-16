package com.wanted.preonboarding.ticket.global.dto;

import com.wanted.preonboarding.ticket.global.exception.ResultCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BaseResDto {
    private int resultCode = ResultCode.SUCCESS.getResultCode();
    private String resultMessage = ResultCode.SUCCESS.getResultMessage();
//    private Object resultData;
}
