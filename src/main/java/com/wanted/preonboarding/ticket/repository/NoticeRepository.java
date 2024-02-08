package com.wanted.preonboarding.ticket.repository;

import com.wanted.preonboarding.core.code.NoticeType;
import com.wanted.preonboarding.ticket.domain.Notice;
import com.wanted.preonboarding.ticket.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByTypeAndPerformance(NoticeType type, Performance performance);
}
