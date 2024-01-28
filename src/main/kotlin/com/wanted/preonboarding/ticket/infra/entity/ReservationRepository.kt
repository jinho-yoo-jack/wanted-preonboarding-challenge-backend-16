package com.wanted.preonboarding.ticket.infra.entity

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ReservationRepository : JpaRepository<ReservationEntity, Long> {
    fun findByPerformanceId(performanceId: UUID): List<ReservationEntity>

    fun findAllByPerformanceIdIn(performanceIds: List<UUID>): List<ReservationEntity>
}
