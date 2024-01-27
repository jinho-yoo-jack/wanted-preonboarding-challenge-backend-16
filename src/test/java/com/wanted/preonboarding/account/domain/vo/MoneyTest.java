package com.wanted.preonboarding.account.domain.vo;


import com.wanted.preonboarding.account.domain.vo.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    @DisplayName("정수 이상의 값을 받아 금액을 생성 할 수 있다.")
    @ValueSource(strings = {"0", "1", "1000", "0.1", "0.01", "1.5"})
    @ParameterizedTest
    void createMoney(String value) {
        // given
        BigDecimal amount = new BigDecimal(value);

        // when
        Money money = Money.createMoney(amount);

        // then
        assertThat(money.getAmount()).isEqualTo(amount);
    }

    @DisplayName("음수의 값을 받으면 금액을 생성 할 수 없다.")
    @ValueSource(strings = {"-0.1", "-1", "-100"})
    @ParameterizedTest
    void createMoneyWithNegative(String value) {
        // given
        BigDecimal amount = new BigDecimal(value);

        // when & then
        assertThatThrownBy(() -> Money.createMoney(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("금액 [%f]는 돈으로 만들 수 없습니다.", amount.doubleValue()));
    }

    @DisplayName("기존 가지고 있는 돈에서 금액을 뺄 수 있다.")
    @CsvSource(value = {"1000:9000", "10000:0"}, delimiter = ':')
    @ParameterizedTest
    void subtractMoney(int subtractMoney, int result) {
        // given
        Money money = Money.createMoney(BigDecimal.valueOf(10000));

        BigDecimal withDrawMoney = BigDecimal.valueOf(subtractMoney);

        // when
        money.subtract(withDrawMoney);

        // then
        assertThat(money.getAmount()).isEqualTo(BigDecimal.valueOf(result));
    }

    @DisplayName("기존 가지고 있는 돈보다 많은 돈은 출금이 불가능하다.")
    @Test
    void subtractMoneyWithNegativeResult() {
        // given
        Money money = Money.createMoney(BigDecimal.valueOf(10000));

        BigDecimal withDrawMoney = BigDecimal.valueOf(11000);

        // when & then
        assertThatThrownBy(() -> money.subtract(withDrawMoney))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("금액을 추가로 송금 할 수 있다.")
    @Test
    void addMoney() {
        // given
        Money money = Money.createMoney(BigDecimal.valueOf(10000));

        BigDecimal depositMoney = BigDecimal.valueOf(1000);
        // when
        money.add(depositMoney);

        // then
        assertThat(money.getAmount()).isEqualTo(BigDecimal.valueOf(11000));
    }
}