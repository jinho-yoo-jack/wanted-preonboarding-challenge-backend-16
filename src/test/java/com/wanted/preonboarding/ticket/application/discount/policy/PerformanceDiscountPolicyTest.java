package com.wanted.preonboarding.ticket.application.discount.policy;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.ticket.domain.discount.Discount;
import com.wanted.preonboarding.ticket.domain.discount.DiscountRepository;
import com.wanted.preonboarding.ticket.domain.discount.model.DiscountType;
import com.wanted.preonboarding.ticket.domain.performance.Performance;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceRepository;
import com.wanted.preonboarding.ticket.domain.performance.model.PerformanceType;
import com.wanted.preonboarding.ticket.domain.performance.model.ReserveState;
import com.wanted.preonboarding.ticket.dto.request.discount.DiscountInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PerformanceDiscountPolicyTest {

    @Qualifier("performanceDiscountPolicy")
    @Autowired
    DiscountPolicy discountPolicy;

    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    DiscountRepository discountRepository;

    @BeforeEach
    void setUp() {
        performanceRepository.deleteAllInBatch();
        discountRepository.deleteAllInBatch();
    }

    @DisplayName("여러 할인이 적용된다.")
    @ValueSource(ints = {100_000, 49_000, 10_000})
    @ParameterizedTest
    void getDiscountAmount(int price) {
        // given
        UUID performanceId = setUpData();

        LocalDateTime requestTime = LocalDateTime.of(2023, 12, 31, 11, 59);
        DiscountInfo info = DiscountInfo.builder()
            .performanceId(performanceId.toString())
            .age(30)
            .requestTime(requestTime)
            .build();

        // when
        double discountAmount = discountPolicy.getDiscountAmount(info, price);

        // then
        double expectDiscountAmount = price * 0.1 + 5000;
        assertThat(discountAmount).isEqualTo(expectDiscountAmount);
    }

    private UUID setUpData() {
        UUID performanceId = performanceRepository.save(Performance.builder()
            .name("ant")
            .price(100_000)
            .round(1)
            .type(PerformanceType.CONCERT)
            .startDate(LocalDateTime.of(2024, 12, 31, 11, 59))
            .isReserve(ReserveState.ENABLE)
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
        return performanceId;
    }


}