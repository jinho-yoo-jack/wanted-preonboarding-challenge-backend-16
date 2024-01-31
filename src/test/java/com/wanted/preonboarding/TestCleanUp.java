package com.wanted.preonboarding;

import com.wanted.preonboarding.performance.framwork.infrastructure.repository.DiscountPolicyRepository;
import com.wanted.preonboarding.performance.framwork.infrastructure.repository.PerformRepository;
import com.wanted.preonboarding.performance.framwork.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.performance.framwork.infrastructure.repository.ReservationCancelSubscribeRepository;
import com.wanted.preonboarding.reservation.framwork.jpaadapter.repository.ReservationRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCleanUp {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private DiscountPolicyRepository discountPolicyRepository;
	@Autowired
	private PerformanceRepository performanceRepository;
	@Autowired
	private PerformRepository showingRepository;
	@Autowired
	private ReservationCancelSubscribeRepository performanceShowingObserverRepository;
	@AfterEach
	void tearDown() {
		reservationRepository.deleteAll();
		showingRepository.deleteAll();
		performanceRepository.deleteAll();
		discountPolicyRepository.deleteAll();
		performanceShowingObserverRepository.deleteAll();
	}

}
