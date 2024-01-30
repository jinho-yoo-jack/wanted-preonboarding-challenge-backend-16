package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link Reservation} Entity를 다루기 위한 JPA 레포지토리 인터페이스입니다.
 *
 * <p>기본적인 CRUD 연산 및 이름과 휴대폰 번호를 통한 예약 정보를 조회합니다.</p>
 */
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
  Reservation findByNameAndPhoneNumber(String name, String phoneNumber);
}
