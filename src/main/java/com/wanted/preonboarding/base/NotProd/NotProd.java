package com.wanted.preonboarding.base.NotProd;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.wanted.preonboarding.ticket.application.TicketSeller;
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
		PerformanceSeatInfoRepository performanceSeatInfoRepository, TicketSeller ticketSeller) {
		return args -> {

			List<Performance> performanceList = new ArrayList<>();
			// Performance 객체 생성
			Performance performance1 = Performance.builder()
				.name("레베카")
				.price(100_000)
				.round(1)
				.type(0)
				.start_date(LocalDateTime.of(2024, 1, 20, 19, 30))
				.isReserve("disable")
				.build();

			performanceList.add(performance1);

			Performance performance2 = Performance.builder()
				.name("영웅")
				.price(100_000)
				.round(1)
				.type(0)
				.start_date(LocalDateTime.of(2024, 1, 21, 19, 30))
				.isReserve("enable")
				.build();
			performanceList.add(performance2);

			for(int i=3; i<=10; i++) {
				Performance performance3 = Performance.builder()
					.name("영웅 조회 테스트용" + i)
					.price(100_000)
					.round(1)
					.type(0)
					.start_date(LocalDateTime.of(2024, 1, 21, 19, 30))
					.isReserve("enable")
					.build();
				performanceList.add(performance3);

				Performance performance4 = Performance.builder()
					.name("레베카 조회 테스트용" + i)
					.price(100_000)
					.round(1)
					.type(0)
					.start_date(LocalDateTime.of(2024, 1, 21, 19, 30))
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
		};
	}
}