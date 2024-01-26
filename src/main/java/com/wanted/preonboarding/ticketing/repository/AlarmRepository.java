package com.wanted.preonboarding.ticketing.repository;

import com.wanted.preonboarding.ticketing.domain.entity.Alarm;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByPerformance(Performance performance);

    @Modifying(clearAutomatically = true)
    @Query("delete from Alarm a where a in :alarms")
    void deleteAllInBatch(List<Alarm> alarms);
}
