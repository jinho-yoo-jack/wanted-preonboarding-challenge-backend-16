package com.wanted.preonboarding.core

data class CursorResult<T>(
    val cursor: Any?,
    val hasNext: Boolean,
    val item: List<T>,
)
