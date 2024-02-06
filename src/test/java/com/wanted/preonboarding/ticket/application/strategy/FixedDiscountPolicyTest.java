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

class FixedDiscountPolicyTest {

    @DisplayName("고정된 금액을 할인한다.")
    @CsvSource(value = {"1000:9000", "500:9500", "10000:0"}, delimiter = ':')
    @ParameterizedTest
    void fixedAmountDiscount(BigDecimal discountMoney, BigDecimal result) {
        // given
        FixedDiscountPolicy fixedDiscountPolicy =
                new FixedDiscountPolicy(Collections.emptyList(), Money.createMoney(discountMoney));

        Money originMoney = Money.createMoney(BigDecimal.valueOf(10000));

        // when
        fixedDiscountPolicy.discount(originMoney);

        // then
        assertThat(originMoney.getAmount()).isEqualTo(result);
    }

    @DisplayName("회차 전략을 가지고 고정 금액 할인을 할 수 있다.")
    @MethodSource("roundDiscountCondition")
    @ParameterizedTest
    void roundFixedDiscountCondition(int round, Money discountAmount, Money originMoney, BigDecimal result) {
        // given
        FixedDiscountPolicy fixedDiscountPolicy =
                new FixedDiscountPolicy(List.of(new RoundDiscountCondition(List.of(1))), discountAmount);

        DiscountConditionInfo discountConditionInfo = DiscountConditionInfo.builder()
                .round(round)
                .build();

        // when
        fixedDiscountPolicy.calculateDiscountedAmount(originMoney, discountConditionInfo);

        // then
        assertThat(originMoney.getAmount()).isEqualTo(result);
    }

    static Stream<?> roundDiscountCondition() {
        Money discountAmount = Money.createMoney(BigDecimal.valueOf(1000));

        return Stream.of(
                arguments(1, discountAmount, createOriginMoney(10000), BigDecimal.valueOf(9000)),
                arguments(2, discountAmount, createOriginMoney(10000), BigDecimal.valueOf(10000))
        );
    }

    private static Money createOriginMoney(int value) {
        return Money.createMoney(BigDecimal.valueOf(value));
    }
}