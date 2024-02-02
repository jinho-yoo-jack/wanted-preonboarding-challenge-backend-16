package com.wanted.preonboarding.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 익셉션 별 코드,메세지 관리
 * 
 */
@ControllerAdvice
@RestController
public class ExceptionAdvisor {
	
	@Autowired
	private ExceptionService exceptionService;
	
    /**
     * 적정 리퀘스트 파라미터가 존재하지 않을시 발생
	 * errorcode : 8401
     * @param methodArgumentNotValidException
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessageInfo> processValidationError(MethodArgumentNotValidException methodArgumentNotValidException) {
        return ResponseEntity.badRequest().body(exceptionService.selectMethodArgumentNotValidExceptionMessage(methodArgumentNotValidException));
    }
    /**
     * 메세지 형태가 이상하거나 존재하지 않을시 발생
	 * errorcode : 8402
     * @param httpMessageNotReadableException
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ExceptionMessageInfo> processHttpMessageNotReadableError(HttpMessageNotReadableException httpMessageNotReadableException) {
		return ResponseEntity.badRequest().body(exceptionService.selectHttpMessageNotReadableExceptionMessage(httpMessageNotReadableException));
	}
    /**
     * 잘못된 주소로 요청을 보낼경우 발생
	 * errorcode : 8403
     * @param noResourceFoundException
     * @return
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionMessageInfo> processNoResourceFoundError(NoResourceFoundException noResourceFoundException) {
    	return ResponseEntity.badRequest().body(exceptionService.selectNoResourceFoundExceptionMessage(noResourceFoundException));
    }
    /**
     * 기본 오류 메세지
	 * errorcode : 8400
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionMessageInfo> processDefaultError(Exception exception) {
		return ResponseEntity.badRequest().body(exceptionService.selectDefaultExceiptionMessage(exception));
	}
}
