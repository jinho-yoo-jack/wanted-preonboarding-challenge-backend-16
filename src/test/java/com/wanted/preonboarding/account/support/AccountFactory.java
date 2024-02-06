package com.wanted.preonboarding.account.support;

import com.wanted.preonboarding.account.domain.Account;
import com.wanted.preonboarding.account.domain.vo.Money;

import java.math.BigDecimal;

public final class AccountFactory {

    private AccountFactory() {

    }

    public static Account create(Long userId, BigDecimal amount) {
        return Account.builder()
                .userId(userId)
                .money(Money.createMoney(amount))
                .build();
    }
}
