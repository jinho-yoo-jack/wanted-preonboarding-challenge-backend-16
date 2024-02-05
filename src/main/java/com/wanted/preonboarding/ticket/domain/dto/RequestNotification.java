package com.wanted.preonboarding.ticket.domain.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestNotification {
	@NotBlank(message = "알림 설장자의 이름은 필수 항목입니다.")
	private String name;
	@NotBlank(message = "알림 설장자의 휴대폰 번호는 필수 항목입니다.")
	private String phoneNumber;

	@NotBlank(message = "알림 설정자의 이메일은 필수 항목입니다.")
	private String email;

	@NotBlank(message = "알림 받으실 공연 ID는 필수 항목입니다.")
	private String performanceId;

	// UUID의 경우 NotBlank 불가능 하기에 String으로 받고 반환을 UUID화
	public UUID getPerformanceId() {
		return UUID.fromString(performanceId);
	}
}