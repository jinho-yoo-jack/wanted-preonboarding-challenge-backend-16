package com.wanted.preonboarding.stage.dto;

import com.wanted.preonboarding.stage.domain.Stage;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StageInfo {
    private Long stageId;
    private String stageName;
    private String address;
    private List<SeatInfo> seats;

    public static StageInfo of(Stage stage) {
        return StageInfo.builder()
                .stageId(stage.getId())
                .stageName(stage.getName())
                .address(stage.getAddress())
                .seats(stage.getSeats().stream().map(SeatInfo::of).collect(Collectors.toList()))
                .build();
    }

    public Stage toEntity() {
        return Stage.builder()
                .name(stageName)
                .address(address)
                .seats(seats.stream().map(SeatInfo::toEntity).collect(Collectors.toList()))
                .build();
    }

}
