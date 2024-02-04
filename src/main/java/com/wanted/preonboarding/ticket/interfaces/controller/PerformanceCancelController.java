package com.wanted.preonboarding.ticket.interfaces.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.common.util.ResponseType;
import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.PerformanceCancelPubService;
import com.wanted.preonboarding.ticket.application.PerformanceCancelSubService;
import com.wanted.preonboarding.ticket.domain.dto.AlarmMessage;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceDTO;
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
	public ResponseEntity<ResponseHandler<ResponseType>> pushMessage(@PathVariable String name, @RequestBody AlarmMessage alarmMessage) {
		ChannelTopic channel = channels.get(name + alarmMessage.getPerformanceId());

		return ResponseEntity
			.ok()
			.body(ResponseHandler.<ResponseType>builder()
				.message(ResponseType.SUCCESS.getMessage())
				.statusCode(HttpStatus.valueOf(ResponseType.SUCCESS.getStatusCode()))
				.data(redisPublisher.publish(channel, alarmMessage))
				.build()
			);
	}

	@PutMapping("/alarm")
	public ResponseEntity<ResponseHandler<ResponseType>> createTopic(@RequestBody ReservationCancelRequest request) {
		ChannelTopic channel = new ChannelTopic(request.performanceId() + request.reservationName());
		redisMessageListener.addMessageListener(redisSubscriber, channel);
		channels.put(request.performanceId() + request.reservationName(), channel);

		return ResponseEntity
			.ok()
			.body(ResponseHandler.<ResponseType>builder()
				.message(ResponseType.SUCCESS.getMessage())
				.statusCode(HttpStatus.valueOf(ResponseType.SUCCESS.getStatusCode()))
				.data(ResponseType.SUCCESS)
				.build()
			);
	}

	@DeleteMapping("/alarm")
	public ResponseEntity<ResponseHandler<ResponseType>> deleteTopic(@RequestBody ReservationCancelRequest request) {
		ChannelTopic channel = channels.get(request.performanceId() + request.reservationName());
		redisMessageListener.removeMessageListener(redisSubscriber, channel);
		channels.remove(request.performanceId() + request.reservationName());

		return ResponseEntity
			.ok()
			.body(ResponseHandler.<ResponseType>builder()
				.message(ResponseType.SUCCESS.getMessage())
				.statusCode(HttpStatus.valueOf(ResponseType.SUCCESS.getStatusCode()))
				.data(ResponseType.SUCCESS)
				.build()
			);
	}

	@PostMapping("/alarm")
	public ResponseEntity<ResponseHandler<ResponseType>> subscribeMessage(@RequestBody ReservationCancelRequest request) {
		redisSubscriber.subscribe(request);

		return ResponseEntity
			.ok()
			.body(ResponseHandler.<ResponseType>builder()
				.message(ResponseType.SUCCESS.getMessage())
				.statusCode(HttpStatus.valueOf(ResponseType.SUCCESS.getStatusCode()))
				.data(ResponseType.SUCCESS)
				.build()
			);
	}
}
