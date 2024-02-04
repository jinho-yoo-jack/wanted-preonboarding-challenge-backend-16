package com.wanted.preonboarding.ticket.domain.reservation;

import static com.wanted.preonboarding.ticket.domain.performance.model.PerformanceType.CONCERT;
import static com.wanted.preonboarding.ticket.domain.performance.model.ReserveState.DISABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.wanted.preonboarding.support.config.RepositoryTest;
import com.wanted.preonboarding.ticket.domain.performance.Performance;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceRepository;
import com.wanted.preonboarding.ticket.dto.result.CancelReservationInfo;
import com.wanted.preonboarding.ticket.dto.result.ReservationModel;
import com.wanted.preonboarding.support.ReflectionUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

@RepositoryTest
class ReservationSearchRepositoryImplTest {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        performanceRepository.deleteAllInBatch();
        reservationRepository.deleteAllInBatch();
    }

    @DisplayName("예약 정보 조회 테스트")
    @Nested
    class PerformanceModel {

        @DisplayName("이름과 휴대폰 번호로 예약 정보를 찾을 수 있다.")
        @Test
        void findReservationModel_success() {
            // given
            final LocalDateTime startDate = LocalDateTime.of(2023, 12, 31, 19, 00);
            final UUID performanceId = savePerformance(startDate);

            final String name = "기우";
            final String phoneNumber = "01012345678";
            saveReservation(performanceId, name, phoneNumber, "A", 1);

            final Sort sort = Sort.by(Order.desc("createAt"));
            final PageRequest pageRequest = PageRequest.of(0, 5, sort);

            // when
            Page<ReservationModel> model = reservationRepository.findReservationModel(name, phoneNumber, pageRequest);

            // then
            List<ReservationModel> models = model.getContent();
            assertThat(models).hasSize(1)
                .extracting("performanceId", "performanceName", "startDate", "type",
                    "gate", "round", "line", "seat", "name", "phoneNumber")
                .containsExactly(
                    tuple(performanceId, "Hola", startDate, CONCERT,
                        1, 1, "A", 1, name, phoneNumber));
        }

        @DisplayName("특정 페이지 번호의 예약 정보만 조회한다.")
        @Test
        void findReservationModel_success_paging() {
            // given
            final UUID performanceId = savePerformance();

            final String name = "기우";
            final String phoneNumber = "01012345678";

            for (int number = 1; number <= 3; number++) {
                saveReservation(performanceId, name, phoneNumber, "A", number);
            }
            saveReservation(performanceId, name, phoneNumber, "B", 1);

            final Sort sort = Sort.by(Order.desc("createAt"));
            final PageRequest pageRequest = PageRequest.of(0, 3, sort);

            // when
            Page<ReservationModel> model = reservationRepository.findReservationModel(name, phoneNumber, pageRequest);

            // then
            List<ReservationModel> models = model.getContent();
            assertThat(models).hasSize(3)
                .extracting("line", "seat")
                .containsExactlyInAnyOrder(
                    tuple("A", 1),
                    tuple("A", 2),
                    tuple("A", 3)
                );
        }

        @DisplayName("예약 정보가 예약일 기준 최신순으로 정렬되어 반환된다.")
        @Test
        void findReservationModel_success_sort() {
            // given
            final UUID performanceId = savePerformance();

            final String name = "기우";
            final String phoneNumber = "01012345678";

            for (int number = 1; number <= 3; number++) {
                LocalDateTime createdAt = LocalDateTime.of(2024, number, 10, 19,0);
                Reservation reservation = saveReservation(performanceId, name, phoneNumber, "A", number);
                // createAt field update
                ReflectionUtils.setSuperClassField(reservation, "createdAt", createdAt);
            }
            entityManager.flush();

            final Sort sort = Sort.by(Order.desc("createAt"));
            final PageRequest pageRequest = PageRequest.of(0, 3, sort);

            // when
            Page<ReservationModel> model = reservationRepository.findReservationModel(name, phoneNumber, pageRequest);

            // then
            List<ReservationModel> models = model.getContent();
            assertThat(models).hasSize(3)
                .extracting("line", "seat")
                .containsExactly(
                    tuple("A", 3),
                    tuple("A", 2),
                    tuple("A", 1)
                );
        }

        @DisplayName("고객의 이름과 휴대전화가 잘못되었다면 예약정보 조회에 실패한다.")
        @CsvSource({
            "기우, 01032451234",
            "제인, 01099998888",
        })
        @ParameterizedTest
        void findReservationModel_fail_invalid_name_phone(String name, String phoneNumber) {
            // given
            final UUID performanceId = savePerformance();
            saveReservation(performanceId, name, phoneNumber, "A", 1);

            final Sort sort = Sort.by(Order.desc("createAt"));
            final PageRequest pageRequest = PageRequest.of(0, 5, sort);

            // when
            if (name.equals("기우")) name = "기호";
            if (phoneNumber.equals("01099998888")) phoneNumber = "01011112222";
            Page<ReservationModel> model = reservationRepository.findReservationModel(name, phoneNumber, pageRequest);

            // then
            List<ReservationModel> models = model.getContent();
            assertThat(models).isEmpty();
        }

        @DisplayName("ID로 취소할 예약 정보를 조회할 수 있다.")
        @Test
        void findInfoForCancel_success() {
            // given
            final UUID performanceId = savePerformance();
            final String line = "B";
            final int seat = 2;
            int reservationId = saveReservation(performanceId, line, seat);

            // when
            CancelReservationInfo info = reservationRepository.findInfoForCancel(reservationId)
                .orElseThrow();

            // then
            assertThat(info)
                .extracting("performanceId", "line", "seat")
                .contains(performanceId, line, seat);
        }
    }

    private Reservation saveReservation(UUID performanceId, String name, String phoneNumber, String line, int seat) {
        Reservation reservation = Reservation.builder()
            .performanceId(performanceId)
            .name(name)
            .phoneNumber(phoneNumber)
            .round(1)
            .gate(1)
            .line(line)
            .seat(seat)
            .build();
        return reservationRepository.saveAndFlush(reservation);
    }

    private int saveReservation(UUID performanceId, String line, int seat) {
        Reservation reservation = Reservation.builder()
            .performanceId(performanceId)
            .name("기우")
            .phoneNumber("01083112233")
            .round(1)
            .gate(1)
            .line(line)
            .seat(seat)
            .build();
        return reservationRepository.saveAndFlush(reservation).getId();
    }

    private UUID savePerformance() {
        final LocalDateTime startDate = LocalDateTime.of(2023, 12, 31, 19, 00);
        final Performance performance = Performance.builder()
            .name("Hola")
            .price(100_000)
            .round(1)
            .type(CONCERT)
            .startDate(startDate)
            .isReserve(DISABLE)
            .build();
        return performanceRepository.save(performance).getId();
    }

    private UUID savePerformance(LocalDateTime startDate) {
        final Performance performance = Performance.builder()
            .name("Hola")
            .price(100_000)
            .round(1)
            .type(CONCERT)
            .startDate(startDate)
            .isReserve(DISABLE)
            .build();
        return performanceRepository.save(performance).getId();
    }
}