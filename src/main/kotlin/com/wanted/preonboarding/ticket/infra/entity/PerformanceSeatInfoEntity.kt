package com.wanted.preonboarding.ticket.infra.entity

import com.wanted.preonboarding.ticket.domain.PerformanceSeatInfo
import com.wanted.preonboarding.ticket.domain.PerformanceSeatInfoId
import com.wanted.preonboarding.ticket.domain.SeatInfo
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "performance_seat_info")
class PerformanceSeatInfoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
    @Column(name = "performance_id")
    val performanceId: UUID,
    @Column(name = "round")
    val round: Int,
    @Column(name = "gate")
    val gate: Int,
    @Column(name = "line")
    val line: String,
    @Column(name = "seat")
    val seat: Int,
    @Column(name = "is_reserve")
    @Convert(converter = EnableDisableConverter::class)
    val isReserve: Boolean,
) : BaseEntity() {
    fun toDomain(): PerformanceSeatInfo {
        return PerformanceSeatInfo(
            id = PerformanceSeatInfoId(id),
            seatInfo =
                SeatInfo(
                    round = round,
                    gate = gate,
                    line = line,
                    seat = seat,
                ),
            isReserve = isReserve,
            createdAt = createdAt!!,
            updatedAt = updatedAt,
        )
    }
}
