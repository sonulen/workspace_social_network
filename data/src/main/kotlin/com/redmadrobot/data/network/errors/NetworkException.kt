package com.redmadrobot.data.network.errors

import com.redmadrobot.data.entity.api.NetworkEntityError
import okio.IOException

sealed class NetworkException(override val message: String) : IOException() {

    data class NoInternetAccess(
        override val message: String = "Отсутствует подключение к интернету",
    ) : NetworkException(message)

    class BadRequest(
        errorResponse: NetworkEntityError,
        override val message: String = errorResponse.message,
    ) : NetworkException(message)

    class Unauthorized(
        errorResponse: NetworkEntityError,
        override val message: String = errorResponse.message,
    ) : NetworkException(message)

    class Unknown(
        errorResponse: NetworkEntityError,
        override val message: String = errorResponse.message,
    ) : NetworkException(message)
}
