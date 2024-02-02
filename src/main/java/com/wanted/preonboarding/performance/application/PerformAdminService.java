package com.wanted.preonboarding.performance.application;

import com.wanted.preonboarding.performance.domain.Perform;
import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.discount_policy.NoneDiscountPolicy;
import com.wanted.preonboarding.performance.domain.vo.PerformancePlace;
import com.wanted.preonboarding.performance.framwork.infrastructure.repository.DiscountPolicyRepository;
import com.wanted.preonboarding.performance.framwork.infrastructure.repository.PerformRepository;
import com.wanted.preonboarding.performance.framwork.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//TODO: perform, performance 생성 클래스 분리해야됩니다. 시간상 생략합니다.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformAdminService {

	private final PerformanceRepository performanceRepository;
	private final DiscountPolicyRepository discountPolicyRepository;
	private final PerformRepository performRepository;

	@Transactional
	public UUID register(PerformRequest request) {
		NoneDiscountPolicy discountPolicy = new NoneDiscountPolicy();
		discountPolicyRepository.save(discountPolicy);

		Performance performance = Performance.create(
			request.name(),
			request.price(),
			request.type(),
			discountPolicy
		);

		PerformancePlace performancePlace = PerformancePlace.create(1);
		Perform perform = Perform.create(performance,
			performancePlace, request.round(), request.startDate(), request.isReserve());

		performanceRepository.save(performance);
		return performRepository.save(perform).getId();
	}
}
