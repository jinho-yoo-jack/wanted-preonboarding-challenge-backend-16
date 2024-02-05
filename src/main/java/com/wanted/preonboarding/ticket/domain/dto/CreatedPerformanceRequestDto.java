package com.wanted.preonboarding.ticket.domain.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class CreatedPerformanceRequestDto {
    @Column(nullable = false)
    private String performanceName;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int round;
    @Column(nullable = false)
    private int type;
    @Column(nullable = false)
    private Date start_date;
    @Column(nullable = false)
    private String lineRange;
    @Column(nullable = false)
    private String seatRange;


    public List<String> generateCombinations() {
        List<String> combinations = new ArrayList<>();

        if (lineRange != null && seatRange != null) {
            String[] lines = lineRange.split("~");
            String[] seatRangeParts = seatRange.split("~");

            int startSeat = Integer.parseInt(seatRangeParts[0]);
            int endSeat = Integer.parseInt(seatRangeParts[1]);

            for (String line : lines) {
                for (int i = startSeat; i <= endSeat; i++) {
                    combinations.add(line + i);
                }
            }
        }
        return combinations;
    }
}
