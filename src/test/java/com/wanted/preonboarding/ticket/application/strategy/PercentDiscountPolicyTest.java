package com.wanted.preonboarding.ticket.application.strategy;

import com.wanted.preonboarding.account.domain.vo.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PercentDiscountPolicyTest {

    @DisplayName("할인 비율로 금액을 할인 받을 수 있다.")
    @CsvSource(value = {"10:9000", "15:8500", "100:0"}, delimiter = ':')
    @ParameterizedTest
    void percentDiscount(BigDecimal percent, BigDecimal result) {
        // given
        PercentDiscountPolicy percentDiscountPolicy = new PercentDiscountPolicy(Collections.emptyList(), percent);
        Money originMoney = Money.createMoney(BigDecimal.valueOf(10000));

        // when
        percentDiscountPolicy.discount(originMoney);

        // then
        // TODO 테스트 개선 필요
        assertThat(originMoney.getAmount().compareTo(result)).isEqualTo(0);
    }

    @DisplayName("회차 전략을 가지고 할인 금액 할인을 할 수 있다.")
    @MethodSource("roundDiscountCondition")
    @ParameterizedTest
    void roundPercentDiscountCondition(int round, BigDecimal discountPercent, Money originMoney, BigDecimal result) {
        // given
        PercentDiscountPolicy percentDiscountPolicy =
                new PercentDiscountPolicy(List.of(new RoundDiscountCondition(List.of(1))), discountPercent);

        DiscountConditionInfo discountConditionInfo = DiscountConditionInfo.builder()
                .round(round)
                .build();

        // when
        percentDiscountPolicy.calculateDiscountedAmount(originMoney, discountConditionInfo);

        // then
        assertThat(originMoney.getAmount().compareTo(result)).isEqualTo(0);
    }

    static Stream<?> roundDiscountCondition() {
        BigDecimal discountPercent = BigDecimal.TEN;

        return Stream.of(
                arguments(1, discountPercent, createOriginMoney(10000), BigDecimal.valueOf(9000)),
                arguments(2, discountPercent, createOriginMoney(10000), BigDecimal.valueOf(10000))
        );
    }

    private static Money createOriginMoney(int value) {
        return Money.createMoney(BigDecimal.valueOf(value));
    }
}