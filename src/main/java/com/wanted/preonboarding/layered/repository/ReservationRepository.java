package com.wanted.preonboarding.layered.repository;

import com.wanted.preonboarding.domain.entity.Reservation;
import com.wanted.preonboarding.domain.entity.UserInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link Reservation} Entity를 다루기 위한 JPA 레포지토리 인터페이스입니다.
 *
 * <p>기본적인 CRUD 연산 및 유저 정보를 통한 예약 정보를 조회합니다.</p>
 */
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
  Reservation findByUserInfo(UserInfo userInfo);

  List<Reservation> findAllByUserInfo(UserInfo userInfo);
}
