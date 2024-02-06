package com.wanted.preonboarding.performance.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wanted.preonboarding.performance.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.performance.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.PerformanceSeatInfoRepository;

import lombok.RequiredArgsConstructor;

/**
 * 공연/전시 관련 서비스
 * 
 */
@Service
@RequiredArgsConstructor
public class PerformanceService {
	
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    
    /** 기본 조회조건 : is_reserve*/
    private static final String CHECK_ISRESERVATION = "enable";
    
    
    /**
     * 예약 가능 공연 및 전시 리스트 조회
     * @return
     */
    public List<PerformanceInfo> selectEnablePerformanceInfoList() {
    	List<PerformanceInfo> selectPerformanceInfoList = performanceRepository.findByIsReserve(CHECK_ISRESERVATION)
                .stream()
                .map(PerformanceInfo::enablePerformance)
                .toList();
        return selectPerformanceInfoList;
    }
    
    /**
     * 예약 가능 공연 및 전시 상세 조회
     * @param performanceInfo
     * @return
     */
    public List<PerformanceInfo> selectEnablePerformanceInfoDetail(PerformanceInfo performanceInfo) {
    	List<PerformanceInfo> selectPerformanceInfoList = performanceSeatInfoRepository.findByPerformanceId(performanceInfo.getPerformanceId())
    			.stream()
    			.map(PerformanceInfo::detailPerformanceList)
    			.toList();
    	return selectPerformanceInfoList;
    }

}
