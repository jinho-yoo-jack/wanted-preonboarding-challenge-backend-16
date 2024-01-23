package com.wanted.preonboarding.ticket.domain

data class SeatInfo(
    val round: Int,
    val gate: Int,
    val line: String,
    val seat: Int,
)
