package com.wanted.preonboarding.base.notProd;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.wanted.preonboarding.ticket.application.NotificationService;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.RequestNotification;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;

@Configuration
@Profile({"dev", "test"})
public class NotProd {
	@Bean
	CommandLineRunner initData(PerformanceRepository performanceRepository,
		PerformanceSeatInfoRepository performanceSeatInfoRepository, TicketSeller ticketSeller,
		NotificationService notificationService) {
		return args -> {

			List<Performance> performanceList = new ArrayList<>();
			// Performance 객체 생성
			Performance performance1 = Performance.builder()
				.name("레베카")
				.price(100_000)
				.round(1)
				.type(0)
				.startDate(LocalDateTime.of(2024, 1, 20, 19, 30))
				.isReserve("disable")
				.build();

			performanceList.add(performance1);

			Performance performance2 = Performance.builder()
				.name("영웅")
				.price(100_000)
				.round(1)
				.type(0)
				.startDate(LocalDateTime.of(2024, 1, 21, 19, 30))
				.isReserve("enable")
				.build();
			performanceList.add(performance2);

			for (int i = 3; i <= 10; i++) {
				Performance performance3 = Performance.builder()
					.name("영웅 조회 테스트용" + i)
					.price(100_000)
					.round(1)
					.type(0)
					.startDate(LocalDateTime.of(2024, 1, 21, 19, 30))
					.isReserve("enable")
					.build();
				performanceList.add(performance3);

				Performance performance4 = Performance.builder()
					.name("레베카 조회 테스트용" + i)
					.price(100_000)
					.round(1)
					.type(0)
					.startDate(LocalDateTime.of(2024, 1, 21, 19, 30))
					.isReserve("disable")
					.build();
				performanceList.add(performance3);
				performanceList.add(performance4);

			}
			performanceRepository.saveAll(performanceList);

			// PerformanceSeatInfo 객체들 생성
			List<PerformanceSeatInfo> performanceSeatInfos = new ArrayList<>();
			for (int i = 1; i <= 4; i++) {
				PerformanceSeatInfo seatInfo1 = PerformanceSeatInfo.builder()
					.performance(performance1)
					.round(1)
					.gate(1)
					.line("A")
					.seat(i)
					.isReserve("enable")
					.build();
				performanceSeatInfos.add(seatInfo1);

				PerformanceSeatInfo seatInfo2 = PerformanceSeatInfo.builder()
					.performance(performance2)
					.round(1)
					.gate(1)
					.line("A")
					.seat(i)
					.isReserve("enable")
					.build();
				performanceSeatInfos.add(seatInfo2);
			}

			PerformanceSeatInfo seatInfo3 = PerformanceSeatInfo.builder()
				.performance(performance2)
				.round(1)
				.gate(1)
				.line("A")
				.seat(18)
				.isReserve("enable")
				.build();
			performanceSeatInfos.add(seatInfo3);

			// PerformanceSeatInfo 저장
			performanceSeatInfoRepository.saveAll(performanceSeatInfos);

			System.out.println("이거로 테스트해보기" + seatInfo3.getPerformance().getId());

			// 자리 예약 초기 데이터 1
			ticketSeller.reserve(ReserveInfo.builder()
				.age(29)
				.amount(300_000)
				.reservationName("박철현")
				.reservationPhoneNumber("010-1231-1231")
				.reservationStatus("reserve")
				.performanceId(performance2.getId())
				.line("A")
				.seat(18)
				.round(1)
				.build());

			Performance performanceTest = Performance.builder()
				.name("테스트용공연")
				.price(100_000)
				.round(1)
				.type(0)
				.startDate(LocalDateTime.of(2024, 1, 22, 19, 30))
				.isReserve("enable")
				.build();

			performanceRepository.save(performanceTest);

			System.out.println("테스트용 공연 Id " + performanceTest.getId());
			// 좌석 생성
			performanceSeatInfos.clear();
			for (int i = 0; i <= 4; i++) {
				PerformanceSeatInfo seatInfoTest = PerformanceSeatInfo.builder()
					.performance(performanceTest)
					.round(1)
					.gate(1)
					.line("A")
					.seat(i)
					.isReserve("enable")
					.build();
				performanceSeatInfos.add(seatInfoTest);
			}
			performanceSeatInfoRepository.saveAll(performanceSeatInfos);
			// 자리 모두 예약
			for (int i = 0; i < 4; i++) {
				ReserveInfo reserveInfo = ReserveInfo.builder()
					.performanceId(performanceTest.getId())
					.round(1)
					.reservationName("박철현")
					.reservationPhoneNumber("010-5555-" + String.format("%04d", i))
					.amount(200_000)
					.line("A")
					.seat(i)
					.build();
				ticketSeller.reserve(reserveInfo);
			}

			// 알림 예약 설정
			RequestNotification requestNotification1 = RequestNotification.builder()
				.email("r4560798@test.com")
				.phoneNumber("010-4444-4444")
				.name("박철현")
				.performanceId(String.valueOf(performanceTest.getId()))
				.build();

			RequestNotification requestNotification2 = RequestNotification.builder()
				.email("r4560798@test.com")
				.phoneNumber("010-2222-2222")
				.name("박철현")
				.performanceId(String.valueOf(performanceTest.getId()))
				.build();

			notificationService.create(requestNotification1);
			notificationService.create(requestNotification2);

		};
	}
}