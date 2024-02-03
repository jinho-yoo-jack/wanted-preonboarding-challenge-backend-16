package com.wanted.preonboarding.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.wanted.preonboarding.common.exception.custom.CustomNullPointerException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExceptionService {
	/** =======================에러 코드 시작====================================*/
	private final int  DefaultExceiptionErrorCode = 8400; 
	private final int  MethodArgumentNotValidExceptionErrorCode = 8401; 
	private final int  HttpMessageNotReadableExceptionErrorCode = 8402; 
	private final int  NoResourceFoundExceptionErrorCode = 8403; 
	private final int  NullPointerExceptionErrorCode = 8404; 
	private final int  DataIntegrityViolationExceptionErrorCode = 8405; 
	private final int  CustomNullPointerExceptionErrorCode = 8406; 
	/** =======================에러 코드 종료====================================*/

	/**
	 * 적정 리퀘스트 파라미터가 존재하지 않을시 발생 오류 메세지 생성
	 * errorcode : 8401
	 * @param methodArgumentNotValidException
	 * @return
	 */
	public ExceptionMessageInfo selectMethodArgumentNotValidExceptionMessage(MethodArgumentNotValidException methodArgumentNotValidException){
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
        
		return ExceptionMessageInfo.builder()
				.errorCode(MethodArgumentNotValidExceptionErrorCode)
				.message(sb.toString())
				.build();
	}
	
	/**
	 * 메세지 형태가 이상하거나 존재하지 않을시 발생 오류 메세지 생성
	 * errorcode : 8402
	 * @param httpMessageNotReadableException
	 * @return
	 */
	public ExceptionMessageInfo selectHttpMessageNotReadableExceptionMessage(HttpMessageNotReadableException httpMessageNotReadableException) {
		return ExceptionMessageInfo.builder()
				.errorCode(HttpMessageNotReadableExceptionErrorCode)
				.message("API 메세지 형태를 확인해 주세요.")
				.build();
	}
	
	/**
	 * 잘못된 주소로 요청을 보낼경우 발생 오류 메세지 생성
	 * errorcode : 8403
	 * @param noResourceFoundException
	 * @return
	 */
	public ExceptionMessageInfo selectNoResourceFoundExceptionMessage(NoResourceFoundException noResourceFoundException) {
		return ExceptionMessageInfo.builder()
				.errorCode(NoResourceFoundExceptionErrorCode)
				.message("API 주소를 확인해 주세요.")
				.build();
	}
	
	/**
	 * 조회조건이 없는 경우
	 * errorcode : 8404
	 * @param nullPointerException
	 * @return
	 */
	public ExceptionMessageInfo selectNullPointerExceptionMessage(NullPointerException nullPointerException) {
		return ExceptionMessageInfo.builder()
				.errorCode(NullPointerExceptionErrorCode)
				.message("조건에 맞는 공연이 없습니다.")
				.build();
	}
	
	/**
	 * 중복 예약일 경우
	 * errorcode : 8405
	 * @param dataIntegrityViolationException
	 * @return
	 */
	public ExceptionMessageInfo selectDataIntegrityViolationExceptionMessage(DataIntegrityViolationException dataIntegrityViolationException) {
		return ExceptionMessageInfo.builder()
				.errorCode(DataIntegrityViolationExceptionErrorCode)
				.message("중복하여 예약할 수 없습니다.")
				.build();
	}

	
	/**
	 * 예약 내역이 없을경우
	 * errorcode : 8406
	 * @param customNullPointerException
	 * @return
	 */
	public ExceptionMessageInfo selectCustomNullPointerExceptionMessage(CustomNullPointerException customNullPointerException) {
		return ExceptionMessageInfo.builder()
				.errorCode(CustomNullPointerExceptionErrorCode)
				.message("예약 내역이 없습니다.")
				.build();
	}
	
	/**
	 * 기본 오류 메세지 생성
	 * errorcode : 8400
	 * @param defaultException
	 * @return
	 */
	public ExceptionMessageInfo selectDefaultExceiptionMessage(Exception defaultException){
		log.debug(defaultException.toString());
		return ExceptionMessageInfo.builder()
				.errorCode(DefaultExceiptionErrorCode)
				.message("알수없는 이유의 오류가 발생했습니다.")
				.build();
	}
	
	
}
