package com.wanted.preonboarding.ticket.presentation;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.base.rsData.RsData;
import com.wanted.preonboarding.ticket.application.NotificationService;
import com.wanted.preonboarding.ticket.domain.dto.RequestNotification;
import com.wanted.preonboarding.ticket.domain.entity.Notification;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	/*
		특정 공연 자리 나면 알림 오게하는 설정 저장 API
	 */
	@PostMapping("")
	public RsData createNotification(@RequestBody @Valid RequestNotification requestNotification, BindingResult bindingResult) {
		// 요청 객체에서 입력하지 않은 부분이 있다면 메세지를 담아서 RsData 객체 바로 리턴
		if (bindingResult.hasErrors()) {
			List<String> errorMessages = bindingResult.getAllErrors()
				.stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
			return RsData.of("F-1", errorMessages.get(0));
		}

		RsData rsData = notificationService.create(requestNotification);

		return rsData;
	}
}
