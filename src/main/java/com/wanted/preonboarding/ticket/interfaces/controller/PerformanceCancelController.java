package com.wanted.preonboarding.ticket.interfaces.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.ticket.application.PerformanceCancelPubService;
import com.wanted.preonboarding.ticket.domain.vo.AlarmMessage;

@RestController
@RequestMapping("/cancel")
@RequiredArgsConstructor
public class PerformanceCancelController {
	private final PerformanceCancelPubService redisPubService;

	@PostMapping("/alarm")
	public String pubSub(@RequestBody AlarmMessage alarmMessage) {
		//메시지 보내기
		redisPubService.sendMessage(alarmMessage);

		return "success";
	}
}
