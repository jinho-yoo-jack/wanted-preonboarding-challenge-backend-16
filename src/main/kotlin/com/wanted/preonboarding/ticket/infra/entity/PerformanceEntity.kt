package com.wanted.preonboarding.ticket.infra.entity

import com.wanted.preonboarding.ticket.domain.Performance
import com.wanted.preonboarding.ticket.domain.PerformanceId
import com.wanted.preonboarding.ticket.domain.PerformanceSeatInfo
import com.wanted.preonboarding.ticket.domain.PerformanceType
import com.wanted.preonboarding.ticket.domain.Reservation
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "performance")
class PerformanceEntity(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID? = null,
    @Column(name = "name")
    val name: String,
    @Column(name = "price")
    val price: Int,
    @Column(name = "round")
    val round: Int,
    @Column(name = "type")
    val type: PerformanceType,
    @Column(name = "start_date")
    val startDate: LocalDateTime,
    @Column(name = "is_reserve")
    @Convert(converter = EnableDisableConverter::class)
    val isReserve: Boolean,
) : BaseEntity() {
    fun toDomain(
        performanceSeatInfos: List<PerformanceSeatInfo>,
        reservations: List<Reservation>,
    ): Performance {
        return Performance(
            id = PerformanceId(id!!),
            name = name,
            price = price,
            round = round,
            type = type,
            startDate = startDate,
            performanceSeatInfos = performanceSeatInfos.toMutableList(),
            reservations = reservations.toMutableList(),
            isReserve = isReserve,
            createdAt = createdAt!!,
            updatedAt = updatedAt,
        )
    }
}
