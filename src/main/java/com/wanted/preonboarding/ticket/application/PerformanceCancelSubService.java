package com.wanted.preonboarding.ticket.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.common.exception.PerformanceSeatInfoNotFoundException;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.info.AlarmMessage;
import com.wanted.preonboarding.ticket.domain.info.SeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationCancelRequest;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PerformanceCancelSubService implements MessageListener {
	private final PerformanceRepository performanceRepository;
	private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static List<String> messageList = new ArrayList<>();
	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			AlarmMessage alarmMessage = mapper.readValue(message.getBody(), AlarmMessage.class);
			messageList.add(message.toString());

			logger.info("PerformanceId = " + alarmMessage.getPerformanceId());
			logger.info("AlarmMessage = " + alarmMessage.getContext());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createAlarmMessage(ReservationCancelRequest request) {
		PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.findPerformanceSeatInfoBySeatInfoAndPerformanceId(
			SeatInfo.of(request.line(), request.seat()), UUID.fromString(request.performanceId()))
    		.orElseThrow(PerformanceSeatInfoNotFoundException::new);
		StringBuilder stringBuilder = createCancelMessage(performanceSeatInfo);
		Message message = new Message() {
			@SneakyThrows
			@Override
			public byte[] getBody() {
				AlarmMessage alarmMessage = AlarmMessage.of(UUID.fromString(request.performanceId()), stringBuilder.toString());
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonString = objectMapper.writeValueAsString(alarmMessage);
				return jsonString.getBytes(StandardCharsets.UTF_8);
			}

			@Override
			public byte[] getChannel() {
				return (request.performanceId()+"*").getBytes(StandardCharsets.UTF_8);
			}
		};

		onMessage(message, message.getChannel());
	}

	private StringBuilder createCancelMessage(PerformanceSeatInfo performanceSeatInfo) {
		log.info("reservationID {}", performanceSeatInfo.getId());

		Performance performance = performanceRepository.findPerformanceByPerformanceId(performanceSeatInfo.getPerformanceId())
			.orElseThrow(EntityNotFoundException::new);

		StringBuilder message = new StringBuilder();
		message
			.append("공연 ID: ").append(performance.getId()).append("\n")
			.append("공연명: ").append(performance.getPeformanceName()).append("\n")
			.append("회차: ").append(performance.getRound()).append("\n")
			.append("시작일: ").append(performance.getStart_date()).append("\n")
			.append("예매 가능한 좌석: ")
			.append(performanceSeatInfo.getSeatInfo().getLine()).append("열").append(" ")
			.append(performanceSeatInfo.getSeatInfo().getSeat()).append("번").append("\n");
		return message;
	}
}
