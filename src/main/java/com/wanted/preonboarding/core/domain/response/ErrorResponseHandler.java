package com.wanted.preonboarding.core.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wanted.preonboarding.ticket.domain.dto.LinkInfo;
import java.util.Map;
import lombok.Builder;
import org.springframework.http.HttpStatus;

/**
 * {@code ErrorResponseHandler}는 에외를 처리하는 데 사용되는 클래스입니다.
 * 이 클래스는 HTTP 상태 코드와 에러 메시지를 포함하는 레코드(Record)를 제공합니다.
 *
 * @param statusCode HTTP 상태 코드
 * @param message 에러 메세지
 */
@Builder
public record ErrorResponseHandler(HttpStatus statusCode, String message,
                                   @JsonInclude(Include.NON_NULL) Map<String, LinkInfo> links) {
}
