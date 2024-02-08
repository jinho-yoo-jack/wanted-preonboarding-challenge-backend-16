package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
class PerformanceRepositoryTest {

	@Autowired
	private PerformanceRepository performanceRepository;

	@Autowired
	private PerformanceSeatInfoRepository performanceSeatInfoRepository;

	@Test
	void savePerformance() throws ParseException {
		// given
		Performance performance = Performance.create("레베카", 100000, 1, 0, valueOf("2024-01-20 19:30:00"), "disable");

		// when
		Performance save = performanceRepository.save(performance);
		System.out.println(save.getId());
	}

	@Test
	void savePerformanceAndSeat() throws ParseException {
		// given
		Performance performance = Performance.create("레베카", 100000, 1, 0, valueOf("2024-01-20 19:30:00"), "disable");

		// when
		Performance save = performanceRepository.save(performance);

		for (int i = 1; i <= 4; i++) {
			performanceSeatInfoRepository.save(PerformanceSeatInfo.create(save, 1, 1, "A", i, "enable"));
		}
	}

	private Date valueOf(String date) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
	}
}