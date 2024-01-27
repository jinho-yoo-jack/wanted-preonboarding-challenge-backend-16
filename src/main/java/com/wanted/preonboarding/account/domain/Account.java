package com.wanted.preonboarding.account.domain;

import com.wanted.preonboarding.account.domain.vo.Money;
import com.wanted.preonboarding.core.domain.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Entity
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_id", unique = true)
    private Long userId;

    @Embedded
    private Money money;

    @Builder
    private Account(Long id, Long userId, Money money) {
        this.id = id;
        this.userId = userId;
        this.money = money;
    }

    public void deposit(BigDecimal depositMoney) {
        money.add(depositMoney);
    }

    public void withdraw(BigDecimal withdrawMoney) {
        money.subtract(withdrawMoney);
    }

    public static Account of(Long id, Long userId, Money money) {
        return Account.builder()
                .id(id)
                .userId(userId)
                .money(money)
                .build();
    }

    public static Account createNewAccount(Long userId) {
        return Account.builder()
                .userId(userId)
                .money(Money.Zero())
                .build();
    }
}
