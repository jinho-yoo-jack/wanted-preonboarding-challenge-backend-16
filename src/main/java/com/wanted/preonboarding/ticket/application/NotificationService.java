package com.wanted.preonboarding.ticket.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.base.rsData.RsData;
import com.wanted.preonboarding.ticket.domain.dto.RequestNotification;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.NotificationRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
	private final NotificationRepository notificationRepository;

	private final PerformanceRepository performanceRepository;

	/*
		알림 설정 정보를 저장하는 data 생성 메서드
	 */
	@Transactional
	public RsData create(RequestNotification requestNotification) {
		Performance performance = performanceRepository.findById(requestNotification.getPerformanceId()).get();

		if(performance == null) {
			return RsData.of("F-1", "존재하지 않는 공연입니다.");
		}

		Notification notification = Notification.builder()
			.email(requestNotification.getEmail())
			.phoneNumber(requestNotification.getPhoneNumber())
			.name(requestNotification.getName())
			.performance(performance)
			.build();

		notificationRepository.save(notification);

		return RsData.of("S-1", String.format("%s님의 알림 설정 성공", requestNotification.getName()), notification);
	}
}
