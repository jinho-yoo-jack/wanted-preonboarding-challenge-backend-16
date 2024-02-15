package com.wanted.preonboarding.ticket.repository;

import com.wanted.preonboarding.domain.entity.Reservation;
import com.wanted.preonboarding.domain.entity.SeatInfo;
import com.wanted.preonboarding.domain.entity.UserInfo;
import com.wanted.preonboarding.layered.repository.ReservationRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@SpringBootTest
@Slf4j
@Transactional
public class ReservationTest {
  final private ReservationRepository repository;

  @Autowired
  ReservationTest(ReservationRepository repository) {
    this.repository = repository;
  }

  @Test
  @DisplayName("DI Test")
  public void diTest() {
    assertThat(this.repository).isInstanceOf(ReservationRepository.class);
  }

  @Test
  @DisplayName("예약 목록 크기")
  public void 예약_목록_조회() {
    //  given

    //  when
    List<Reservation> result = this.repository.findAll();

    //  then
    assertThat(result.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("예약 목록 조회")
  public void 예약_목록_확인() {
    //  given

    //  when
    List<Reservation> result = this.repository.findAll();

    //  then

  }

  @Test
  @DisplayName("예약 생성 성공")
  public void 예약_생성() {
    //  given
    UserInfo userInfo = UserInfo.builder().id(1).userName("테스터").phoneNumber("010-8962-1061").build();
    SeatInfo seatInfo = SeatInfo.builder().id(3).seat(1).gate(1).line("A").build();
    Reservation reservation = Reservation.of(userInfo, seatInfo);

    //  when
    this.repository.saveAndFlush(reservation);
    List<Reservation> result = this.repository.findAllByUserInfo(userInfo);

    //  then
    for (Reservation i : result) {
      log.info("id : {}, userId : {}, performanceId : {}", i.getId(), i.getUserInfo().getId(), i.getSeatInfo().getId());
    }
    assertThat(result).asList().contains(reservation);
  }

  @Test
  @DisplayName("예약 생성 실패")
  public void 예약_실패() {
    //  given
    UserInfo user1 = UserInfo.builder().id(1).userName("테스터").phoneNumber("010-8962-1061").build();
    UserInfo user2 = UserInfo.builder().id(2).userName("테스터2").phoneNumber("010-1234-5678").build();
    SeatInfo seatInfo = SeatInfo.builder().id(3).seat(1).gate(1).line("A").build();
    Reservation reservation1 = Reservation.of(user1, seatInfo);
    Reservation reservation2 = Reservation.of(user2, seatInfo);

    //  when
    this.repository.saveAndFlush(reservation1);
    Exception exception = catchException(() -> { this.repository.saveAndFlush(reservation2); });

    //  then
    assertThat(exception).isInstanceOf(Exception.class);
  }
}
