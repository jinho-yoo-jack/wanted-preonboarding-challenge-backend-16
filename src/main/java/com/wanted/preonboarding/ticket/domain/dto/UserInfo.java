package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfo {

    // 사용자 이름
    private String userName;

    // 전화번호
    private String phoneNumber;

    // 잔고
    private int total_amount;
}
