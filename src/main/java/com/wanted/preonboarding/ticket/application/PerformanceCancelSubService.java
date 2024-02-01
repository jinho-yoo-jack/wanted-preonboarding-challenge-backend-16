package com.wanted.preonboarding.ticket.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.ticket.domain.vo.AlarmMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceCancelSubService implements MessageListener {
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
}
