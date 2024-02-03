package com.wanted.preonboarding.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.wanted.preonboarding.common.exception.custom.CustomNullPointerException;

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
     * 조회조건이 없는 경우
     * errorcode : 8404
     * @param nullPointerException
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionMessageInfo> nullPointerExceptionError(NullPointerException nullPointerException) {
    	return ResponseEntity.badRequest().body(exceptionService.selectNullPointerExceptionMessage(nullPointerException));
    }
    /**
     * 중복 예약일 경우
     * errorcode : 8405
     * @param dataIntegrityViolationException
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionMessageInfo> dataIntegrityViolationExceptionError(DataIntegrityViolationException dataIntegrityViolationException) {
    	return ResponseEntity.badRequest().body(exceptionService.selectDataIntegrityViolationExceptionMessage(dataIntegrityViolationException));
    }
    /**
     * 중복 예약일 경우
     * errorcode : 8405
     * @param dataIntegrityViolationException
     * @return
     */
    @ExceptionHandler(CustomNullPointerException.class)
    public ResponseEntity<ExceptionMessageInfo> customNullPointerExceptionError(CustomNullPointerException customNullPointerException) {
    	return ResponseEntity.badRequest().body(exceptionService.selectCustomNullPointerExceptionMessage(customNullPointerException));
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
