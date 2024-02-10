package com.wanted.preonboarding.config;

import static java.util.UUID.fromString;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Profile("real")
public class InitData {

	private final InitService initService;

	@PostConstruct
	public void init() throws ParseException {
		initService.init();
	}

	@Component
	static class InitService {
		@PersistenceContext
		private EntityManager em;

		@Transactional
		public void init() throws ParseException {
			// given
			Performance performance = Performance.create("레베카", 100000, 1, 0, valueOf("2024-01-20 19:30:00"), "enable");

			// when
			em.persist(performance);

			for (int i = 1; i <= 4; i++) {
				em.persist(PerformanceSeatInfo.create(performance, 1, 1, 'A', i, "enable"));
			}
		}

		private Date valueOf(String date) throws ParseException {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		}
	}
}
