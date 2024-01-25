package com.wanted.preonboarding.performance.service;

import com.wanted.preonboarding.core.exception.CustomException;
import com.wanted.preonboarding.core.exception.ExceptionCode;
import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.constant.ReserveStatus;
import com.wanted.preonboarding.performance.dto.PerformanceSearchParam;
import com.wanted.preonboarding.performance.dto.request.PerformanceSearchRequest;
import com.wanted.preonboarding.performance.repository.PerformanceRepository;
import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    /**
     * Retrieves a list of all PerformanceInfo objects.
     *
     * @param pageable The pagination information.
     * @return A Page object containing the retrieved PerformanceInfo objects.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceInfo> getAllPerformanceInfoList(PerformanceSearchParam searchParam, Pageable pageable) {
        return performanceRepository.findByReserveStatus(searchParam.getReserveStatus(),pageable)
                .map(PerformanceInfo::of);
    }

    /**
     * Retrieves a PerformanceInfo object by its ID.
     *
     * @param performanceId The ID of the performance.
     * @return The PerformanceInfo object with the specified ID.
     */
    @Transactional(readOnly = true)
    public PerformanceInfo getPerformanceById(UUID performanceId) {
        return PerformanceInfo.of(performanceRepository.findById(performanceId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_EXIST_ENTITY)));
    }

    /**
     * Creates a new PerformanceInfo object by saving it to the PerformanceRepository.
     *
     * @param performanceInfo The PerformanceInfo object to be created.
     * @return The newly created PerformanceInfo object.
     */
    public PerformanceInfo createPerformance(PerformanceInfo performanceInfo) {
        return PerformanceInfo.of(performanceRepository.save(performanceInfo.toEntity()));
    }

    /**
     * Updates the performance with the given performance ID using the provided performance info.
     *
     * @param performanceId The ID of the performance to update.
     * @param performanceInfo The updated performance info.
     * @return The updated PerformanceInfo object.
     * @throws EntityNotFoundException If the performance with the given ID does not exist.
     */
    public PerformanceInfo updatePerformance(UUID performanceId, PerformanceInfo performanceInfo) throws EntityNotFoundException {
        try {
            Performance performance = performanceRepository.getReferenceById(performanceId);
            performance.update(performanceInfo);
            return PerformanceInfo.of(performance);
        }catch (EntityNotFoundException e) {
            log.error("not exist performanceId : {}",performanceId);
            throw new CustomException(ExceptionCode.NOT_EXIST_ENTITY);
        }
    }

    /**
     * Deletes a performance from the performance repository.
     *
     * @param performanceId The ID of the performance to delete.
     */
    public void deletePerformance(UUID performanceId) {
        performanceRepository.deleteById(performanceId);
    }

}
