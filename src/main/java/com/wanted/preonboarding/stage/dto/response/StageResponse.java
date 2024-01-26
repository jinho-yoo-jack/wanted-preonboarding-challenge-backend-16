package com.wanted.preonboarding.stage.dto.response;

import com.wanted.preonboarding.stage.dto.StageInfo;

public record StageResponse(Long stageId, String stageName, String address, Integer seatsNumber) {
    public static StageResponse of(StageInfo stageInfo) {
        return new StageResponse(stageInfo.getStageId(),stageInfo.getStageName(), stageInfo.getAddress(),stageInfo.getSeats().size());
    }

}
