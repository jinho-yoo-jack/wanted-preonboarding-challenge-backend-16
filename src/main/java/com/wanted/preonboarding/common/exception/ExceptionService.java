package com.wanted.preonboarding.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Service
public class ExceptionService {
	/** =======================에러 코드 시작====================================*/
	private final int  DefaultExceiptionErrorCode = 8400; 
	private final int  MethodArgumentNotValidExceptionErrorCode = 8401; 
	private final int  HttpMessageNotReadableExceptionErrorCode = 8402; 
	private final int  NoResourceFoundExceptionErrorCode = 8403; 
	/** =======================에러 코드 종료====================================*/

	/**
	 * 적정 리퀘스트 파라미터가 존재하지 않을시 발생 오류 메세지 생성
	 * errorcode : 8401
	 * @param methodArgumentNotValidException
	 * @return
	 */
	public Map<String,Object> selectMethodArgumentNotValidExceptionMessage(MethodArgumentNotValidException methodArgumentNotValidException){
		Map<String, Object> errorMap = new HashMap<String, Object>();
		
		BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
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
	 * 메세지 형태가 이상하거나 존재하지 않을시 발생 오류 메세지 생성
	 * errorcode : 8402
	 * @param httpMessageNotReadableException
	 * @return
	 */
	public Map<String,Object> selectHttpMessageNotReadableExceptionMessage(HttpMessageNotReadableException httpMessageNotReadableException) {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		
		errorMap.put("errorCode", HttpMessageNotReadableExceptionErrorCode);
		errorMap.put("message", "API 메세지 형태를 확인해 주세요.");
		
		return errorMap;
	}
	
	/**
	 * 잘못된 주소로 요청을 보낼경우 발생 오류 메세지 생성
	 * errorcode : 8403
	 * @param noResourceFoundException
	 * @return
	 */
	public Map<String,Object> selectNoResourceFoundExceptionMessage(NoResourceFoundException noResourceFoundException) {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		
		errorMap.put("errorCode", NoResourceFoundExceptionErrorCode);
		errorMap.put("message", "API 주소를 확인해 주세요.");
		
		return errorMap;
	}
	
	/**
	 * 기본 오류 메세지 생성
	 * errorcode : 8400
	 * @param defaultException
	 * @return
	 */
	public Map<String,Object> selectDefaultExceiptionMessage(Exception defaultException){
		Map<String, Object> errorMap = new HashMap<String, Object>();
		
		errorMap.put("errorCode", DefaultExceiptionErrorCode);
		errorMap.put("message", "알수없는 이유의 오류가 발생했습니다.");
		
		return errorMap;
	}

	
}
