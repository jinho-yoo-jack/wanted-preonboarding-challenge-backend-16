package com.wanted.preonboarding.stage.repository;

import com.wanted.preonboarding.stage.domain.Seat;
import com.wanted.preonboarding.stage.domain.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[DB 접근] Stage repository 테스트")
@DataJpaTest
public class StageRepositoryTest {

    @Autowired
    private StageRepository stageRepository;

    @DisplayName("stage를 저장하면 스테이지가 반환된다.")
    @Test
    public void givenStage_whenSaveStage_thenReturnsStage() {
        //given
        Stage stage = createStage();
        //when
        Stage savedStage = stageRepository.save(stage);
        //then
        assertThat(savedStage).isNotNull();
        assertThat(savedStage.getId()).isNotNull();
        assertThat(savedStage.getSeats().size()).isEqualTo(3);
        for(Seat seat : savedStage.getSeats()) {
            assertThat(seat.getId()).isNotNull();
        }
    }

    private Stage createStage() {
        Stage stage = Stage.builder()
                .name("코엑스")
                .address("삼성동")
                .build();
        stage.addSeat(createSeat("1번"));
        stage.addSeat(createSeat("2번"));
        stage.addSeat(createSeat("3번"));
        return stage;
    }

    private Seat createSeat(String name) {
        return Seat.builder()
                .name(name)
                .build();
    }

}
