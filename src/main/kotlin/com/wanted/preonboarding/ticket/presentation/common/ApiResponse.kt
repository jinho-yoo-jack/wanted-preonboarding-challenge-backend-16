package com.wanted.preonboarding.ticket.presentation.common

open class ApiResponse<T>(
    val result: ResultType,
    val data: T? = null,
    val error: Any? = null,
) {
    companion object {
        fun success(): ApiResponse<Unit> {
            return ApiResponse(ResultType.SUCCESS, null, null)
        }

        fun <S> success(data: S): ApiResponse<S> {
            return ApiResponse(ResultType.SUCCESS, data, null)
        }

        fun error(error: Any? = null): ApiResponse<Unit> {
            return ApiResponse(ResultType.ERROR, null, error)
        }

        fun fail(error: Any? = null): ApiResponse<Unit> {
            return ApiResponse(ResultType.FAIL, null, error)
        }
    }
}
