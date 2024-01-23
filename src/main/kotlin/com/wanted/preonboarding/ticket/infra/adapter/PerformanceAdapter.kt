package com.wanted.preonboarding.ticket.infra.adapter

import com.wanted.preonboarding.core.CursorResult
import com.wanted.preonboarding.core.exception.ApplicationException
import com.wanted.preonboarding.ticket.application.ticket.PerformancePort
import com.wanted.preonboarding.ticket.domain.Performance
import com.wanted.preonboarding.ticket.domain.PerformanceId
import com.wanted.preonboarding.ticket.infra.entity.PerformanceEntityJpaRepository
import com.wanted.preonboarding.ticket.infra.entity.PerformanceSeatInfoEntityJpaRepository
import com.wanted.preonboarding.ticket.infra.entity.ReservationEntity
import com.wanted.preonboarding.ticket.infra.entity.ReservationEntityJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PerformanceAdapter(
    private val performanceEntityJpaRepository: PerformanceEntityJpaRepository,
    private val performanceSeatInfoEntityJpaRepository: PerformanceSeatInfoEntityJpaRepository,
    private val reservationEntityJpaRepository: ReservationEntityJpaRepository,
) : PerformancePort {
    override fun findPerformance(id: PerformanceId): Performance? {
        val performance = performanceEntityJpaRepository.findByIdOrNull(id.value) ?: return null
        val performanceSeatInfo = performanceSeatInfoEntityJpaRepository.findByPerformanceId(id.value)
        val reservation = reservationEntityJpaRepository.findByPerformanceId(id.value)
        return performance.toDomain(
            performanceSeatInfo.map { it.toDomain() },
            reservation.map { it.toDomain() },
        )
    }

    override fun update(performance: Performance) {
        val reservationEntities = reservationEntityJpaRepository.findByPerformanceId(performance.id.value)
        val toSaveReservations =
            performance.getReservations().filter { reservation ->
                reservationEntities.none { it.id == reservation.id.value }
            }
        val toDeleteReservations =
            reservationEntities.filter { reservation ->
                performance.getReservations().none { it.id.value == reservation.id }
            }
        reservationEntityJpaRepository.deleteAllInBatch(toDeleteReservations)
        reservationEntityJpaRepository.saveAll(
            toSaveReservations.map {
                ReservationEntity(
                    id = it.id.value,
                    performanceId = performance.id.value,
                    name = it.getUserInfo().name,
                    phoneNumber = it.getUserInfo().phoneNumber,
                    gate = it.getSeatInfo().gate,
                    line = it.getSeatInfo().line,
                    seat = it.getSeatInfo().seat,
                    round = it.getSeatInfo().round,
                )
            },
        )

        val performanceSeatInfoEntities =
            performanceSeatInfoEntityJpaRepository.findByPerformanceId(performance.id.value)
        performanceSeatInfoEntities.forEach { performanceSeatInfo ->
            performance.getPerformanceSeatInfos().find { it.id.value == performanceSeatInfo.id }?.let {
                performanceSeatInfo.isReserve = it.isReserveAvailable()
            }
        }
    }

    override fun findAllPerformanceByReserveAvailable(
        reserveAvailable: Boolean,
        cursor: PerformanceId?,
        size: Int,
    ): CursorResult<Performance> {
        val performances =
            if (cursor == null) {
                performanceEntityJpaRepository.findByIsReserveOrderByCreatedAtDesc(
                    reserveAvailable,
                    PageRequest.of(0, size + 1),
                )
            } else {
                val cursorEntity =
                    performanceEntityJpaRepository.findByIdOrNull(cursor.value)
                        ?: throw ApplicationException.NotFoundException("존재하지 않는 공연입니다.")
                performanceEntityJpaRepository.search(
                    reserveAvailable,
                    cursorEntity.id!!,
                    cursorEntity.createdAt!!,
                    PageRequest.of(0, size + 1),
                )
            }
        val performanceSeatInfos =
            performanceSeatInfoEntityJpaRepository.findAllByPerformanceIdIn(
                performances.map { it.id!! },
            )
        val reservations =
            reservationEntityJpaRepository.findAllByPerformanceIdIn(
                performances.map { it.id!! },
            )
        val performanceList =
            performances.map { performance ->
                performance.toDomain(
                    performanceSeatInfos.filter { it.performanceId == performance.id }.map { it.toDomain() },
                    reservations.filter { it.performanceId == performance.id }.map { it.toDomain() },
                )
            }
        val item =
            if (performanceList.size > size) {
                performanceList.dropLast(1)
            } else {
                performanceList
            }

        return CursorResult(
            cursor = item.lastOrNull()?.id?.value,
            hasNext = performanceList.size > size,
            item = item,
        )
    }
}
