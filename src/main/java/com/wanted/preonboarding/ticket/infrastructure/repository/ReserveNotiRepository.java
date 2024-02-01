package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.ReserveNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReserveNotiRepository extends JpaRepository<ReserveNotice, UUID> {

    @Query("SELECT r" +
            " FROM ReserveNotice r " +
            "WHERE r.performanceId = :performanceId" +
            "  AND r.noticeYn = :noticeYn")
    List<String> findByUserName(@Param("performanceId") UUID performanceId, @Param("noticeYn") String noticeYn);
}
