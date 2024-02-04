package com.wanted.preonboarding.support.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.ticket.domain.reservation.ReservationSearchRepository;
import com.wanted.preonboarding.ticket.domain.reservation.ReservationSearchRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@TestConfiguration
public class QueryDslTestConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public ReservationSearchRepository reservationSearchRepository() {
        return new ReservationSearchRepositoryImpl(jpaQueryFactory());
    }

}
