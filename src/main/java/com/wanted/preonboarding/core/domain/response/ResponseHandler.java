package com.wanted.preonboarding.core.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wanted.preonboarding.ticket.domain.dto.LinkInfo;
import java.util.Map;
import lombok.Builder;
import org.springframework.http.HttpStatus;

/**
 * {@code ResponseHandler}는 HTTP 응답을 처리하는 데 사용되는 클래스입니다.
 * 이 클래스는 HTTP 상태 코드, 메시지 및 데이터를 포함하는 레코드(Record)를 제공합니다.
 *
 * @param <T> 데이터의 유형을 나타내는 제네릭 타입 매개변수
 */
@Builder
public record ResponseHandler<T>(HttpStatus statusCode, String message,
                                 @JsonInclude(Include.NON_NULL) T data,
                                 @JsonInclude(Include.NON_NULL) Map<String, LinkInfo> links) {
}
