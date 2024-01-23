package com.wanted.preonboarding.ticket.infra.entity

import com.wanted.preonboarding.ticket.domain.Reservation
import com.wanted.preonboarding.ticket.domain.ReservationId
import com.wanted.preonboarding.ticket.domain.SeatInfo
import com.wanted.preonboarding.ticket.domain.UserInfo
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "reservation")
class ReservationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
    @Column(name = "performance_id")
    val performanceId: UUID,
    @Column(name = "name")
    val name: String,
    @Column(name = "phone_number")
    val phoneNumber: String,
    @Column(name = "round")
    val round: Int,
    @Column(name = "gate")
    val gate: Int,
    @Column(name = "line")
    val line: String,
    @Column(name = "seat")
    val seat: Int,
) : BaseEntity() {
    fun toDomain(): Reservation {
        return Reservation(
            id = ReservationId(id),
            userInfo = UserInfo(name, phoneNumber),
            seatInfo = SeatInfo(round, gate, line, seat),
            createdAt = createdAt!!,
            updatedAt = updatedAt,
        )
    }
}
