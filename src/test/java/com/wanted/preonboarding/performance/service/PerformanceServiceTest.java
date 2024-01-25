package com.wanted.preonboarding.performance.service;


import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.constant.PerformanceType;
import com.wanted.preonboarding.performance.domain.constant.ReserveStatus;
import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import com.wanted.preonboarding.performance.dto.PerformanceSearchParam;
import com.wanted.preonboarding.performance.repository.PerformanceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직-공연 & 전시회")
@ExtendWith(MockitoExtension.class)
public class PerformanceServiceTest {
    @InjectMocks
    private PerformanceService performanceService;
    @Mock
    private PerformanceRepository performanceRepository;
    @DisplayName("pageable로 검색을 할 경우 빈 페이지가 반환")
    @Test
    public void givenPageableAndParam_whenSearchAllPerformance_thenReturnsEmptyPage() {
        //given
        PerformanceSearchParam param = PerformanceSearchParam.builder().reserveStatus(ReserveStatus.ENABLE).build();
        given(performanceRepository.findByReserveStatus(any(ReserveStatus.class),any(Pageable.class))).willReturn(Page.empty());
        //when
        Page<PerformanceInfo> page = performanceService.getAllPerformanceInfoList(param,Pageable.ofSize(10));
        //then
        then(performanceRepository).should().findByReserveStatus(any(ReserveStatus.class),any(Pageable.class));
        assertThat(page).isEmpty();
    }

    @DisplayName("UUID로 검색을 하면 PerformanceInfo가 반환된다.")
    @Test
    public void givenUUID_whenSearchPerformance_thenReturnsPerformanceInfo() {
        //given
        UUID uuid = UUID.randomUUID();
        PerformanceInfo testData = createNewPerformanceInfo(uuid);
        given(performanceRepository.findById(any(UUID.class))).willReturn(Optional.of(testData.toEntity()));
        //when
        PerformanceInfo performanceInfo = performanceService.getPerformanceById(uuid);
        //then
        then(performanceRepository).should().findById(any(UUID.class));
        assertThat(performanceInfo).isNotNull();
        assertThat(performanceInfo.getPerformanceId()).isEqualTo(uuid);
    }

    @DisplayName("performanceInfo를 저장하면 PerformanceInfo가 반환된다.")
    @Test
    public void givenPerformanceInfo_whenSavePerformance_thenReturnsPerformanceInfo() {
        //given
        PerformanceInfo testData = createNewPerformanceInfo(null);
        given(performanceRepository.save(any(Performance.class))).willReturn(testData.toEntity());
        //when
        PerformanceInfo performanceInfo = performanceService.createPerformance(testData);
        //then
        then(performanceRepository).should().save(any(Performance.class));
        assertThat(performanceInfo).isNotNull();
        assertThat(performanceInfo.getPerformanceId()).isEqualTo(testData.getPerformanceId());
    }

    @DisplayName("performanceInfo와 uuid를 입력받아 수정하면 performanceInfo가 반환된다.")
    @Test
    public void givenPerformanceInfoAndUUID_whenUpdatePerformance_thenReturnsPerformanceInfo() {
        //given
        UUID uuid = UUID.randomUUID();
        PerformanceInfo testData = createNewPerformanceInfo(uuid);
        PerformanceInfo updateData = createNewPerformanceInfo(null);
        given(performanceRepository.getReferenceById(any(UUID.class))).willReturn(testData.toEntity());
        //when
        PerformanceInfo performanceInfo = performanceService.updatePerformance(uuid,updateData);
        //then
        then(performanceRepository).should().getReferenceById(any(UUID.class));
        assertThat(performanceInfo).isNotNull();
        assertThat(performanceInfo.getPerformanceId()).isEqualTo(uuid);
    }

    @Test
    @DisplayName("UUID로 performance를 삭제한다.")
    public void given_when_then() {
        //given
        UUID uuid = UUID.randomUUID();
        //when
        performanceService.deletePerformance(uuid);
        //then
        then(performanceRepository).should().deleteById(any(UUID.class));
    }

    private PerformanceInfo createNewPerformanceInfo(UUID uuid) {
        return PerformanceInfo.builder()
                .performanceId(uuid)
                .price(30000)
                .round(4)
                .performanceType(PerformanceType.EXHIBITION)
                .reserveStatus(ReserveStatus.ENABLE)
                .build();
    }

}
