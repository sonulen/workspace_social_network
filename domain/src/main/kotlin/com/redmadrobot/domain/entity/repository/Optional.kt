package com.redmadrobot.domain.entity.repository

sealed class Optional<out T : Any> {

    data class Success<out T : Any>(val data: T) : Optional<T>()
    data class Error(val exception: Exception) : Optional<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
