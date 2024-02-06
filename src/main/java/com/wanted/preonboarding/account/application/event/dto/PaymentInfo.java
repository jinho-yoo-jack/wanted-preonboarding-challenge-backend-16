package com.wanted.preonboarding.account.application.event.dto;

import com.wanted.preonboarding.account.domain.vo.Money;
import com.wanted.preonboarding.user.User;

public record PaymentInfo(
        User user,
        Money payMoney
) {

    public static PaymentInfo of(User user, Money payMoney) {
        return new PaymentInfo(user, payMoney);
    }
}
