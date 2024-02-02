package com.wanted.preonboarding.ticket.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;

    @DisplayName("이름과 핸드폰 번호로 예약 정보를 조회할 수 있다.")
    @Test
    void findReservationsByNameAndPhoneNumber() {
        // given
        final String name = "KAI";
        final String phone = "01012345678";

        // when
        List<Reservation> reservations = reservationRepository.findReservationsByNameAndPhoneNumber(name, phone);

        // then
        assertThat(reservations).hasSize(1);
    }
}