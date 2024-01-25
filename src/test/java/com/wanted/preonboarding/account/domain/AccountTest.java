package com.wanted.preonboarding.account.domain;


import com.wanted.preonboarding.account.vo.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

    @DisplayName("처음 계좌를 생성하면 금액은 0원이다.")
    @Test
    void createNewAccount() {
        // given
        Long userId = 1L;

        // when
        Account newAccount = Account.createNewAccount(userId);

        // then
        assertThat(newAccount.getMoney()).isEqualTo(Money.Zero());
    }
}