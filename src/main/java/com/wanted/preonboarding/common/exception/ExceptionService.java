package com.wanted.preonboarding.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class ExceptionService {
	
	private final int  DefaultExceiptionErrorCode = 8400; 
	private final int  MethodArgumentNotValidExceptionErrorCode = 8401; 
	
	
	public Map<String,Object> selectDefaultExceiptionMessage(Exception exception){
		Map<String, Object> errorMap = new HashMap<String, Object>();
		
		return errorMap;
	}
	
	/**
	 * 적정 리퀘스트 파라미터가 존재하지 않을시 발생 오류 메세지 생성
	 * @param exception
	 * @return
	 */
	public Map<String,Object> selectMethodArgumentNotValidExceptionMessage(MethodArgumentNotValidException exception){
		Map<String, Object> errorMap = new HashMap<String, Object>();
		
		BindingResult bindingResult = exception.getBindingResult();
		StringBuffer sb = new StringBuffer();
		
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
        	sb
        	.append("[")
        	.append(fieldError.getField())
        	.append("](은)는 ")
        	.append(fieldError.getDefaultMessage())
        	.append(" 입력된 값: [")
        	.append(fieldError.getRejectedValue())
        	.append("]")
        	.append(", ");
        }
        if (sb.length() > 0) {
        	sb.setLength(sb.length() - 2);
        }
        errorMap.put("errorCode", MethodArgumentNotValidExceptionErrorCode);
        errorMap.put("message", sb.toString());
        
		return errorMap;
	}
	
	/**
	 * 기본 오류 메세지 생성
	 * @param exception
	 * @return
	 */
	public Map<String,Object> selectDefaultExceiptionMessage(MethodArgumentNotValidException exception){
		Map<String, Object> errorMap = new HashMap<String, Object>();
		
		errorMap.put("errorCode", DefaultExceiptionErrorCode);
		errorMap.put("message", "알수 없는 이유의 오류가 발생했습니다.");
		
		return errorMap;
	}
}
