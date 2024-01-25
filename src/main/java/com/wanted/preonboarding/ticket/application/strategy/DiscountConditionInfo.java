package com.wanted.preonboarding.ticket.application.strategy;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiscountConditionInfo {

    private final int round;

    /**
     * 지금은 round 밖에 없지만 할인 조건이 추가되면 여러가지 파라미터가 들어올 수 있음.
     * 다만 할인 조건이 추가 될 때마다 필드가 계속 추가되고 null 값이 많은 클래스를 어떻게 분리할지는 고민
     * @param round
     */
    @Builder
    private DiscountConditionInfo(int round) {
        this.round = round;
    }

}
