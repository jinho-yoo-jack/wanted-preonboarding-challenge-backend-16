package com.wanted.preonboarding.ticket.presentation.common

data class CursorResponse<T>(
    val cursor: Any?,
    val hasNext: Boolean,
    val item: List<T>,
)
