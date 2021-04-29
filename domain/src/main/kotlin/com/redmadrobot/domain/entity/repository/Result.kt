package com.redmadrobot.domain.entity.repository

sealed class Result<out T : Any>(val isSuccess: Boolean) {
    data class Success<out T : Any>(val data: T) : Result<T>(true)
    data class Error(val exception: Exception) : Result<Nothing>(false)
}
