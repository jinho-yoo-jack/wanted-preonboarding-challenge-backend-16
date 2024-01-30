package com.wanted.preonboarding.ticket.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.ticket.domain.discount.model.DiscountType;
import com.wanted.preonboarding.ticket.domain.performance.Performance;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceRepository;
import com.wanted.preonboarding.ticket.domain.performance.model.PerformanceType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DiscountRepositoryTest {

    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    DiscountRepository discountRepository;

    @BeforeEach
    void setUp() {
        discountRepository.deleteAllInBatch();
        performanceRepository.deleteAllInBatch();
    }

    @DisplayName("공연 ID로 할인 정보를 가져올 수 있다.")
    @Test
    void findDiscountsByPerformanceId() {
        // given
        UUID performanceId = performanceRepository.save(Performance.builder()
            .name("ant")
            .price(100_000)
            .round(1)
            .type(PerformanceType.CONCERT)
            .startDate(LocalDateTime.of(2024, 12, 31, 11, 59))
            .isReserve("enable")
            .build()).getId();

        LocalDateTime endDate = LocalDateTime.of(2099, 12, 31, 11, 59);
        Discount discount1 = Discount.builder()
            .performanceId(performanceId)
            .name("오픈 할인")
            .type(DiscountType.PERCENT)
            .amount(10)
            .endDate(endDate)
            .build();
        Discount discount2 = Discount.builder()
            .performanceId(performanceId)
            .name("가정의 달 할인")
            .type(DiscountType.AMOUNT)
            .amount(5000)
            .endDate(endDate)
            .build();
        discountRepository.saveAll(List.of(discount1, discount2));

        final LocalDateTime requestTime = LocalDateTime.of(2023, 12, 31, 11, 59);

        // when
        List<Discount> discounts = discountRepository.findDiscountsByPerformanceId(performanceId, requestTime);

        // then
        assertThat(discounts).hasSize(2);
    }

    @DisplayName("할인 기간이 지난 정보를 제외하고 불러온다.")
    @Test
    void findDiscountsByPerformanceId_exclude_expired_discount() {
        // given
        UUID performanceId = performanceRepository.save(Performance.builder()
            .name("ant")
            .price(100_000)
            .round(1)
            .type(PerformanceType.CONCERT)
            .startDate(LocalDateTime.of(2024, 12, 31, 11, 59))
            .isReserve("enable")
            .build()).getId();

        LocalDateTime endDate = LocalDateTime.of(2099, 12, 31, 11, 59);
        Discount discount1 = Discount.builder()
            .performanceId(performanceId)
            .name("오픈 할인")
            .type(DiscountType.PERCENT)
            .amount(10)
            .endDate(endDate)
            .build();

        LocalDateTime expiredDate = LocalDateTime.of(2023, 12, 31, 11, 58);
        Discount discount2 = Discount.builder()
            .performanceId(performanceId)
            .name("가정의 달 할인")
            .type(DiscountType.AMOUNT)
            .amount(5000)
            .endDate(expiredDate)
            .build();
        discountRepository.saveAll(List.of(discount1, discount2));

        final LocalDateTime requestTime = LocalDateTime.of(2023, 12, 31, 11, 59);

        // when
        List<Discount> discounts = discountRepository.findDiscountsByPerformanceId(performanceId, requestTime);

        // then
        assertThat(discounts).hasSize(1);
    }


}