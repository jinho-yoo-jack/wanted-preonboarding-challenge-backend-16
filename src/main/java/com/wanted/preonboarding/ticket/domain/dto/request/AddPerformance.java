package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.Getter;


@Getter
public class AddPerformance {
    private String name;
    private int price;
    private int round;
    private int type;//0,1,2
    private String start_date;//yyyy-mm-dd
    private boolean isReserve;

}
