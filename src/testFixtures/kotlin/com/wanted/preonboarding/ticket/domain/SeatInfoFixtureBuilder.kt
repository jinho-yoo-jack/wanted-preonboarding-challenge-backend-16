package com.wanted.preonboarding.ticket.domain

data class SeatInfoFixtureBuilder(
    val round: Int = 1,
    val gate: Int = 1,
    val line: String = "A",
    val seat: Int = 1,
) {
    fun build(): SeatInfo {
        return SeatInfo(
            round = round,
            gate = gate,
            line = line,
            seat = seat,
        )
    }
}
