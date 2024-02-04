package com.wanted.preonboarding.ticket.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAllInBatch();
    }

    @DisplayName("이름과 핸드폰 번호로 예약 정보를 조회할 수 있다.")
    @Test
    void findReservationsByNameAndPhoneNumber() {
        // given
        final String name = "KAI";
        final String phone = "01012345678";
        saveReservation(name, phone);

        // when
        List<Reservation> reservations = reservationRepository.findReservationsByNameAndPhoneNumber(name, phone);

        // then
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("예약정보를 삭제할 수 있다.")
    @Test
    void deleteById() {
        // given
        final String name = "KAI";
        final String phone = "01012345678";
        int id = saveReservation(name, phone);

        // when
        reservationRepository.deleteById(id);

        // then
        List<Reservation> all = reservationRepository.findAll();
        assertThat(all).isEmpty();
    }

    private int saveReservation(String name, String phone) {
        Reservation reservation = Reservation.builder()
            .performanceId(UUID.randomUUID())
            .name(name)
            .phoneNumber(phone)
            .round(1)
            .gate(1)
            .line("A")
            .seat(1)
            .build();
        return reservationRepository.saveAndFlush(reservation).getId();
    }
}