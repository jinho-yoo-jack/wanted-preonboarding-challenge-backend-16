package com.wanted.preonboarding.ticket.infra.adapter

import com.wanted.preonboarding.ticket.presentation.common.CursorResponse
import com.wanted.preonboarding.core.exception.ApplicationException
import com.wanted.preonboarding.ticket.application.ticket.PerformancePort
import com.wanted.preonboarding.ticket.domain.Performance
import com.wanted.preonboarding.ticket.domain.PerformanceId
import com.wanted.preonboarding.ticket.infra.entity.PerformanceRepository
import com.wanted.preonboarding.ticket.infra.entity.PerformanceSeatInfoRepository
import com.wanted.preonboarding.ticket.infra.entity.ReservationEntity
import com.wanted.preonboarding.ticket.infra.entity.ReservationRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PerformanceAdapter(
    private val performanceRepository: PerformanceRepository,
    private val performanceSeatInfoRepository: PerformanceSeatInfoRepository,
    private val reservationRepository: ReservationRepository,
) : PerformancePort {
    override fun findPerformance(id: PerformanceId): Performance? {
        val performance = performanceRepository.findByIdOrNull(id.value) ?: return null
        val performanceSeatInfo = performanceSeatInfoRepository.findByPerformanceId(id.value)
        val reservation = reservationRepository.findByPerformanceId(id.value)
        return performance.toDomain(
            performanceSeatInfo.map { it.toDomain() },
            reservation.map { it.toDomain() },
        )
    }

    override fun update(performance: Performance) {
        val reservationEntities = reservationRepository.findByPerformanceId(performance.id.value)
        val toSaveReservations =
            performance.getReservations().filter { reservation ->
                reservationEntities.none { it.id == reservation.id.value }
            }
        val toDeleteReservations =
            reservationEntities.filter { reservation ->
                performance.getReservations().none { it.id.value == reservation.id }
            }
        reservationRepository.deleteAllInBatch(toDeleteReservations)
        reservationRepository.saveAll(
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
            performanceSeatInfoRepository.findByPerformanceId(performance.id.value)
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
    ): CursorResponse<Performance> {
        val performances =
            if (cursor == null) {
                performanceRepository.findByIsReserveOrderByCreatedAtDesc(
                    reserveAvailable,
                    PageRequest.of(0, size + 1),
                )
            } else {
                val cursorEntity =
                    performanceRepository.findByIdOrNull(cursor.value)
                        ?: throw ApplicationException.NotFoundException("존재하지 않는 공연입니다.")
                performanceRepository.search(
                    reserveAvailable,
                    cursorEntity.id!!,
                    cursorEntity.createdAt!!,
                    PageRequest.of(0, size + 1),
                )
            }
        val performanceSeatInfos =
            performanceSeatInfoRepository.findAllByPerformanceIdIn(
                performances.map { it.id!! },
            )
        val reservations =
            reservationRepository.findAllByPerformanceIdIn(
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

        return CursorResponse(
            cursor = item.lastOrNull()?.id?.value,
            hasNext = performanceList.size > size,
            item = item,
        )
    }
}
