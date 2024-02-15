package com.wanted.preonboarding.domain.exception;

import com.wanted.preonboarding.domain.response.ErrorResponseHandler;
import com.wanted.preonboarding.ticket.domain.dto.LinkInfo;
import com.wanted.preonboarding.ticket.domain.dto.reservation.NotificationDto;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.UriComponentsBuilder;

import static com.wanted.preonboarding.ticket.domain.dto.DtoSchemaSerializer.getClassSchema;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionController {

  @ExceptionHandler(ReserveConflictException.class)
  public ResponseEntity<ErrorResponseHandler> conflictHandler(
      ReserveConflictException e,
      HttpServletRequest r) {
      String baseurl = r.getRequestURL().toString();

      Map<String, LinkInfo> links = new HashMap<>();
      links.put("알림받기", LinkInfo.builder()
          .link(UriComponentsBuilder.fromUriString(baseurl).path("/notice").build().toString())
          .description("해당 URL을 통해 알림 설정을 할 수 있습니다.")
          .method("POST")
          .schema(getClassSchema(NotificationDto.class))
          .build());
      return ResponseEntity.status(e.getStatus())
          .body(ErrorResponseHandler.builder()
              .message(e.getMessage())
              .statusCode(e.getStatus())
              .links(links).build()
              );
  }

  @ExceptionHandler(TicketException.class)
  public ResponseEntity<ErrorResponseHandler> ticketExceptionHandler(TicketException e) {
    return ResponseEntity.status(e.getStatus())
        .body(ErrorResponseHandler.builder()
            .statusCode(e.getStatus())
            .message(e.getMessage())
            .build()
        );
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponseHandler> badRequest(BadRequestException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponseHandler.builder()
            .message(e.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST)
            .build()
        );
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Object> notAcceptable(HttpRequestMethodNotSupportedException e) {
    return new ResponseEntity<>("지원되지 않는 Method입니다." + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<Object> notFound(NoHandlerFoundException e) {
    return new ResponseEntity<>("페이지를 찾을 수 없습니다." + e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseHandler> internal(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        ErrorResponseHandler.builder()
            .message(e.getMessage())
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
            .build()
    );
  }
}
