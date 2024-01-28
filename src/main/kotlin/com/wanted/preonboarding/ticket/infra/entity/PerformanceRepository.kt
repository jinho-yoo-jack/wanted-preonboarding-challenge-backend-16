package com.wanted.preonboarding.ticket.infra.entity

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.UUID

interface PerformanceRepository : JpaRepository<PerformanceEntity, UUID> {
    fun findByIsReserveOrderByCreatedAtDesc(
        isReserve: Boolean,
        pageable: Pageable,
    ): List<PerformanceEntity>

    @Query(
        """
        SELECT p FROM PerformanceEntity p
        WHERE p.isReserve = :isReserve
        AND ((p.id < :id AND p.createdAt = :createdAt) OR p.createdAt < :createdAt)
        ORDER BY p.createdAt DESC, p.id DESC
        """,
    )
    fun search(
        isReserve: Boolean,
        id: UUID,
        createdAt: LocalDateTime,
        pageable: Pageable,
    ): List<PerformanceEntity>
}
