package com.wanted.preonboarding.account.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Money {

    private static final String NEGATIVE_MESSAGE_FORMAT = "금액 [%f]는 돈으로 만들 수 없습니다.";

    @Column(nullable = false, name = "amount")
    private BigDecimal amount;

    @Builder
    private Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money createMoney(BigDecimal amount) {
        if (isNegativeAmount(amount)) {
            throw new IllegalArgumentException(String.format(NEGATIVE_MESSAGE_FORMAT, amount.doubleValue()));
        }

        return Money.builder()
                .amount(amount)
                .build();
    }

    public static Money Zero() {
        return Money.builder()
                .amount(BigDecimal.ZERO)
                .build();
    }

    public void subtract(BigDecimal withdrawMoney) {
        if (isWithdrawResultNegative(withdrawMoney)) {
            throw new IllegalStateException();
        }

        amount = amount.subtract(withdrawMoney);
    }

    public void multiply(BigDecimal rate) {
        amount = amount.multiply(rate);
    }

    public void add(BigDecimal depositMoney) {
        amount = amount.add(depositMoney);
    }

    private static boolean isNegativeAmount(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean isWithdrawResultNegative(BigDecimal withdrawMoney) {
        return amount.subtract(withdrawMoney).compareTo(BigDecimal.ZERO) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(getAmount(), money.getAmount());
    }

    @Override
    public int hashCode() {
        return this.amount.hashCode();
    }
}
