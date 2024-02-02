package com.wanted.preonboarding.ticket.repository;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceType;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
public class PerpormanceTest {
  @Autowired
  private PerformanceRepository repository;
  @Test
  @DisplayName("DI 테스트")
  public void diTest() {
    assertThat(this.repository).isInstanceOf(PerformanceRepository.class);
  }
  @Test
  @DisplayName("예매 가능한 공연 목록")
  public void canReservation() {
    //  given
    String  isReserved = "enable";

    //  when
    List<Performance> result = this.repository.findByIsReserve(isReserved);

    //  then
    assertThat(result.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("예매 불가능한 공연 목록")
  public void cantReservation() {
    //  given
    String  isReserved = "disable";

    //  when
    List<Performance> result = this.repository.findByIsReserve(isReserved);

    //  then
    assertThat(result.size()).isEqualTo(3);
  }

  @Test
  @DisplayName("이름을 통한 존재하지 않는 공연 조회")
  public void 없는_공연_조회() {
    //  given
    String  performanceName = "없는 공연";

    //  when
    Performance performance = this.repository.findByName(performanceName);

    //  then
    assertThat(performance).isNull();
  }

  @Test
  @DisplayName("이름을 통한 공연 조회")
  public void 있는_공연_조회() {
    //  given
    String  performanceName = "태양의 서커스";

    //  when
    Performance performance = this.repository.findByName(performanceName);

    //  then
    assertThat(performance).isNotNull();
  }

  @Test
  @DisplayName("같은 이름, 다른 회차의 공연 조회")
  public void 같은_이름_다른_회차() {
    //  given
    Performance test1 = Performance.builder().name("테스트").price(10000).round(1).type(
        PerformanceType.CONCERT).isReserve("enable").startDate(
        LocalDateTime.of(2024, 2, 5, 9, 0)).build();
    Performance test2 = Performance.builder().name("테스트").price(10000).round(2).type(
        PerformanceType.CONCERT).isReserve("enable").startDate(LocalDateTime.of(2024, 2, 10, 9, 0)).build();
    this.repository.save(test1);
    this.repository.save(test2);

    //  when
    Performance performance = this.repository.findByName("테스트");

    //  then
    assertThat(performance).isEqualTo(test1);
  }

  @Test
  @DisplayName("ID로 공연 조회하기")
  public void ID로_공연_조회() {
    //  given
    Performance performance = Performance.builder()
        .name("테스트").price(1000).round(1)
        .startDate(LocalDateTime.of(2024, 2, 14, 10, 0))
        .type(PerformanceType.EXHIBITION)
        .isReserve("enable").build();
    this.repository.saveAndFlush(performance);

    //  when
    Performance result = this.repository.findById(performance.getId()).get();

    //  then
    assertThat(result).isEqualTo(performance);
  }
}
