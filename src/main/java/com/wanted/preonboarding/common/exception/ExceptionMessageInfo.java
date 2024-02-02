package com.wanted.preonboarding.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionMessageInfo {
	
	private int errorCode;
	private String message;
	
}
