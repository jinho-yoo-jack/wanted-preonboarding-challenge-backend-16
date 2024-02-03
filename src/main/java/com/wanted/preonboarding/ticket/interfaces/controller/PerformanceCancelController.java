package com.wanted.preonboarding.ticket.interfaces.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.ticket.application.PerformanceCancelPubService;
import com.wanted.preonboarding.ticket.application.PerformanceCancelSubService;
import com.wanted.preonboarding.ticket.domain.info.AlarmMessage;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationCancelRequest;

@RestController
@RequestMapping("/cancel")
@RequiredArgsConstructor
public class PerformanceCancelController {
	private final RedisMessageListenerContainer redisMessageListener;
	private final PerformanceCancelPubService redisPublisher;
	private final PerformanceCancelSubService redisSubscriber;
	private Map<String, ChannelTopic> channels;

	@PostConstruct
	public void init() {
		channels = new HashMap<>();
	}

	@GetMapping("/alarms")
	public Set<String> getTopicAll() {
		return channels.keySet();
	}

	@PostMapping("/alarm/{name}")
	public String pushMessage(@PathVariable String name, @RequestBody AlarmMessage alarmMessage) {
		ChannelTopic channel = channels.get(name+alarmMessage.getPerformanceId());
		redisPublisher.publish(channel, alarmMessage);

		return "SUCCESS";
	}

	@PutMapping("/alarm")
	public String createTopic(@RequestBody ReservationCancelRequest request) {
		ChannelTopic channel = new ChannelTopic(request.performanceId()+request.reservationName());
		redisMessageListener.addMessageListener(redisSubscriber, channel);
		channels.put(request.performanceId()+request.reservationName(), channel);

		return "SUCCESS";
	}

	@DeleteMapping("/alarm")
	public String deleteTopic(@RequestBody ReservationCancelRequest request) {
		ChannelTopic channel = channels.get(request.performanceId()+request.reservationName());
		redisMessageListener.removeMessageListener(redisSubscriber, channel);
		channels.remove(request.performanceId()+request.reservationName());

		return "SUCCESS";
	}

	@PostMapping("/alarm")
	public String subscribeMessage(@RequestBody ReservationCancelRequest request) {
		redisSubscriber.subscribe(request);

		return "SUCCESS";
	}
}
