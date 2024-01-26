package com.wanted.preonboarding.stage.service;

import com.wanted.preonboarding.performance.domain.constant.ReserveStatus;
import com.wanted.preonboarding.stage.domain.Stage;
import com.wanted.preonboarding.stage.domain.constant.SeatStatus;
import com.wanted.preonboarding.stage.dto.SeatInfo;
import com.wanted.preonboarding.stage.dto.StageInfo;
import com.wanted.preonboarding.stage.repository.StageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 공연장")
@ExtendWith(MockitoExtension.class)
public class StageServiceTest {
    @InjectMocks
    private StageService stageService;
    @Mock
    private StageRepository stageRepository;

    @Test
    public void givenStageInfo_whenSavingStage_thenStageInfo() {
        //given
        StageInfo stageInfo = createStageInfo("Mountain");
        given(stageRepository.save(any(Stage.class))).willReturn(stageInfo.toEntity());
        //when
        StageInfo savedStage = stageService.createStage(stageInfo);
        //then
        then(stageRepository).should().save(any(Stage.class));
        assertThat(savedStage).isNotNull();
        assertThat(savedStage.getStageName()).isEqualTo("Mountain");
        assertThat(savedStage.getSeats().size()).isGreaterThan(0);
    }

    private StageInfo createStageInfo(String name) {
        return StageInfo.builder()
                .stageName(name)
                .address("address1")
                .seats(List.of(createSeatInfo(),createSeatInfo(),createSeatInfo()))
                .build();
    }

    private SeatInfo createSeatInfo() {
        return SeatInfo.builder()
                .seatName("seat1")
                .price(30000)
                .seatStatus(SeatStatus.RESERVED)
                .grade("R")
                .build();
    }
}
