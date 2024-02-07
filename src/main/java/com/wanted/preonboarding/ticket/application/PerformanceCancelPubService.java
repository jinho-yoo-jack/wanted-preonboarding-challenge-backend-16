package com.wanted.preonboarding.ticket.application;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.wanted.preonboarding.common.util.ResponseType;
import com.wanted.preonboarding.ticket.domain.dto.AlarmMessage;

@Service
@RequiredArgsConstructor
public class PerformanceCancelPubService {
	private final RedisTemplate<String, Object> redisTemplate;

	public ResponseType publish(ChannelTopic channelTopic, AlarmMessage alarmMessage) {
		redisTemplate.convertAndSend(channelTopic.getTopic(), alarmMessage);

		return ResponseType.SUCCESS;
	}
}
