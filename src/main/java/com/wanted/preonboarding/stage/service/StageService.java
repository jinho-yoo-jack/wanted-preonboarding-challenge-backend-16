package com.wanted.preonboarding.stage.service;

import com.wanted.preonboarding.stage.domain.Stage;
import com.wanted.preonboarding.stage.dto.StageInfo;
import com.wanted.preonboarding.stage.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class StageService {
    private final StageRepository stageRepository;

    /**
     * Creates a new stage based on the given stage information.
     *
     * @param stageInfo the stage information to create the stage from
     * @return the created stage information
     */
    public StageInfo createStage(StageInfo stageInfo) {
        Stage stage = stageInfo.toEntity();
        return StageInfo.of(stageRepository.save(stage));
    }

}
