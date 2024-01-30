package com.wanted.preonboarding.ticket.application.discount;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.ticket.domain.discount.Discount;
import com.wanted.preonboarding.ticket.domain.discount.DiscountRepository;
import com.wanted.preonboarding.ticket.domain.discount.model.DiscountType;
import com.wanted.preonboarding.ticket.domain.performance.Performance;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceRepository;
import com.wanted.preonboarding.ticket.domain.performance.model.PerformanceType;
import com.wanted.preonboarding.ticket.dto.request.discount.PaymentInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DefaultDiscountManagerTest {

    @Autowired
    DiscountManager discountManager;

    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    DiscountRepository discountRepository;

    String performanceId;

    @BeforeEach
    void setUp() {
        discountRepository.deleteAllInBatch();
        performanceRepository.deleteAllInBatch();
    }

    @DisplayName("할인이 적용된 금액이 반환된다.")
    @ValueSource(ints = {100_000, 50_000, 44_900})
    @ParameterizedTest
    void applyDiscount(int price) {
        // given
        setUpData(price);
        PaymentInfo paymentInfo = PaymentInfo.builder()
            .price(price)
            .requestTime(LocalDateTime.of(2024, 4, 20, 11, 59))
            .performanceId(performanceId)
            .age(70)
            .build();

        // when
        int discountedAmount = (int) discountManager.applyDiscount(paymentInfo);

        // then
        int expectAmount = (int) (((price * 0.9) - 5000) * 0.9);
        assertThat(discountedAmount).isEqualTo(expectAmount);
    }

    private void setUpData(int price) {
        performanceId = performanceRepository.save(Performance.builder()
            .name("ant")
            .price(price)
            .round(1)
            .type(PerformanceType.CONCERT)
            .startDate(LocalDateTime.of(2024, 12, 31, 11, 59))
            .isReserve("enable")
            .build()).getId().toString();

        LocalDateTime endDate = LocalDateTime.of(2099, 12, 31, 11, 59);
        Discount discount1 = Discount.builder()
            .performanceId(UUID.fromString(performanceId))
            .name("오픈 할인")
            .type(DiscountType.PERCENT)
            .amount(10)
            .endDate(endDate)
            .build();
        Discount discount2 = Discount.builder()
            .performanceId(UUID.fromString(performanceId))
            .name("가정의 달 할인")
            .type(DiscountType.AMOUNT)
            .amount(5000)
            .endDate(endDate)
            .build();
        discountRepository.saveAll(List.of(discount1, discount2));
    }
}