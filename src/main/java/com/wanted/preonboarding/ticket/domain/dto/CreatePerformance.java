package com.wanted.preonboarding.ticket.domain.dto;
import lombok.*;
import java.sql.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreatePerformance {
    private String name;
    private int price;
    private int round;
    private int type;
    private Date startDate;
    private String isReserve ;

}
