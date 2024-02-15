package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.request.DiscountRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class DiscountServiceTest {
    @Autowired
    private DiscountService discountService;

    @Test
    @DisplayName("금액 할인")
    public void discountPriceMock() {
        DiscountRequest request1 = DiscountRequest.builder()
                .performancePrice(100000)
                .performanceDateTime(LocalDateTime.of(2024, 2, 15, 12, 0))
                .reserveDateTime(LocalDateTime.of(2024, 1, 15, 12, 0))
                .build();
        DiscountRequest request2 = DiscountRequest.builder()
                .performancePrice(70000)
                .performanceDateTime(LocalDateTime.of(2024, 2, 15, 12, 0))
                .reserveDateTime(LocalDateTime.of(2024, 2, 13, 12, 0))
                .build();

        int result1 = discountService.discountPrice(request1);
        int result2 = discountService.discountPrice(request2);
        Assertions.assertThat(result1).isEqualTo(80000);
        Assertions.assertThat(result2).isEqualTo(63000);
    }
}
