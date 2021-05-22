package com.redmadrobot.data.network.errors

import com.redmadrobot.data.entity.api.NetworkEntityError
import com.redmadrobot.data.util.bodyCopy
import com.redmadrobot.data.util.fromJsonOrThrow
import com.squareup.moshi.Moshi
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.HttpsURLConnection

@Singleton
class NetworkErrorHandler @Inject constructor(private val moshi: Moshi) {
    enum class ErrorCode(val message: String) {
        INVALID_CREDENTIALS("Email или пароль указаны неверно"),
        INVALID_TOKEN("Проблема с access или refresh токеном"),
        EMAIL_VALIDATION_ERROR("Email имеет недопустимый формат"),
        PASSWORD_VALIDATION_ERROR("Пароль имеет недопустимый формат"),
        DUPLICATE_USER_ERROR("Такой пользователь уже зарегистрирован"),
        SERIALIZATION_ERROR("Входные данные не соответсвуют модели"),
        FILE_NOT_FOUND_ERROR("Запрошенное изображение не найдено"),
        TOO_BIG_FILE_ERROR("загружаемое изображение больше 5mb"),
        BAD_FILE_EXTENSION_ERROR("Изображение имеет недопустимый формат"),
        GENERIC_ERROR("Общая ошибка"),
        UNKNOWN_ERROR("Что-то пошло не так..."),
    }

    private val unknownErrorResponse
        get() = NetworkEntityError(
            code = ErrorCode.UNKNOWN_ERROR.toString(),
            message = ErrorCode.UNKNOWN_ERROR.message
        )

    fun noInternetAccessException() = NetworkException.NoInternetAccess()

    fun networkErrorToThrow(response: Response): NetworkException {
        val error = parseErrorBody(response.bodyCopy())

        return when (response.code) {
            HttpsURLConnection.HTTP_BAD_REQUEST -> NetworkException.BadRequest(error)
            HttpsURLConnection.HTTP_UNAUTHORIZED -> NetworkException.Unauthorized(error)
            HttpsURLConnection.HTTP_NOT_FOUND -> NetworkException.BadRequest(error)
            else -> NetworkException.Unknown(error)
        }
    }

    private fun parseErrorBody(body: ResponseBody?): NetworkEntityError {
        return if (body != null) {
            Timber.tag("OkHttp").d("От сервера пришел ответ с ошибкой $body")
            try {
                val rawErrorFromServer = moshi.fromJsonOrThrow(NetworkEntityError::class, body.string())
                rawErrorFromServer.convertToHumanReadableError()
            } catch (nonValidJsonOrNull: IOException) {
                Timber.tag("OkHttp").d(nonValidJsonOrNull)
                unknownErrorResponse
            }
        } else {
            unknownErrorResponse
        }
    }

    private fun NetworkEntityError.convertToHumanReadableError(): NetworkEntityError {
        return copy(message = ErrorCode.valueOf(code).message)
    }
}
