package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.discount_policy.NoneDiscountPolicy;
import com.wanted.preonboarding.ticket.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.ticket.domain.Performance;
import com.wanted.preonboarding.ticket.domain.Showing;
import com.wanted.preonboarding.ticket.domain.Showroom;
import com.wanted.preonboarding.ticket.infrastructure.repository.DiscountPolicyRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ShowingRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ShowroomRepository;
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

		Showroom showroom = Showroom.create(1);
		Showing showing = Showing.create(performance, showroom, request.round(), request.startDate(), request.isReserve());

		performanceRepository.save(performance);
		showroomRepository.save(showroom);
		return showingRepository.save(showing).getId();
	}
}
