package com.wanted.preonboarding.ticket.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.wanted.preonboarding.ticket.domain.vo.AlarmMessage;

@Service
@RequiredArgsConstructor
public class PerformanceCancelPubService {
	private final RedisTemplate<String, Object> redisTemplate;

	public void sendMessage(AlarmMessage alarmMessage) {
		redisTemplate.convertAndSend("topic1", alarmMessage);
	}
}
