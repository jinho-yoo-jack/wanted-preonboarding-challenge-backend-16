package com.wanted.preonboarding.performance.application;

import com.wanted.preonboarding.performance.domain.discount_policy.NoneDiscountPolicy;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.PerformanceShowing;
import com.wanted.preonboarding.performance.domain.PerformanceShowroom;
import com.wanted.preonboarding.performance.infrastructure.repository.DiscountPolicyRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.ShowingRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.ShowroomRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//TODO: showroom, showing 생성 클래스 분리해야됩니다. 시간상 생략합니다.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShowingAdminService {

	private final PerformanceRepository performanceRepository;
	private final DiscountPolicyRepository discountPolicyRepository;
	private final ShowingRepository showingRepository;
	private final ShowroomRepository showroomRepository;



	@Transactional
	public UUID register(PerformanceRequest request) {
		NoneDiscountPolicy discountPolicy = new NoneDiscountPolicy();
		discountPolicyRepository.save(discountPolicy);

		Performance performance = Performance.create(
			request.name(),
			request.price(),
			request.type(),
			discountPolicy
		);

		PerformanceShowroom performanceShowroom = PerformanceShowroom.create(1);
		PerformanceShowing performanceShowing = PerformanceShowing.create(performance,
			performanceShowroom, request.round(), request.startDate(), request.isReserve());

		performanceRepository.save(performance);
		showroomRepository.save(performanceShowroom);
		return showingRepository.save(performanceShowing).getId();
	}
}
