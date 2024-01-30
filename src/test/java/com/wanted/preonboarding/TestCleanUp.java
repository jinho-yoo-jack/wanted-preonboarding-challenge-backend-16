package com.wanted.preonboarding;

import com.wanted.preonboarding.performance.infrastructure.repository.DiscountPolicyRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.ShowingRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.ShowroomRepository;
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
	private ShowroomRepository showroomRepository;
	@Autowired
	private ShowingRepository showingRepository;
	@AfterEach
	void tearDown() {
		reservationRepository.deleteAll();
		showingRepository.deleteAll();
		showroomRepository.deleteAll();
		performanceRepository.deleteAll();
		discountPolicyRepository.deleteAll();
	}

}
