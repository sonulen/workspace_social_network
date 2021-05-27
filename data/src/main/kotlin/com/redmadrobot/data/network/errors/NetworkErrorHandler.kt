package com.redmadrobot.data.network.errors

import android.annotation.SuppressLint
import com.redmadrobot.data.entity.api.response.NetworkEntityError
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

    companion object {
        private const val NETWORK_LOG_TAG = "OkHttp"
    }

    private val unknownErrorResponse
        get() = NetworkEntityError(
            code = ErrorCode.UNKNOWN_ERROR.toString(),
            message = ErrorCode.UNKNOWN_ERROR.message
        )

    fun noInternetAccessException() = NetworkException.NoInternetAccess()

    @SuppressLint("BinaryOperationInTimber")
    fun networkErrorToThrow(response: Response): NetworkException {
        val body = response.bodyCopy()
        Timber.tag(NETWORK_LOG_TAG).d(
            "От сервера пришел ответ с ошибкой.\n" +
                "code : ${response.code}\n" +
                "body: $body \n" +
                "body string : ${body?.string()}\n" +
                "message: ${response.message}"
        )
        val error = parseErrorBody(body)

        return when (response.code) {
            HttpsURLConnection.HTTP_BAD_REQUEST -> NetworkException.BadRequest(error)
            HttpsURLConnection.HTTP_UNAUTHORIZED -> NetworkException.Unauthorized(error)
            HttpsURLConnection.HTTP_NOT_FOUND -> NetworkException.BadRequest(error)
            else -> NetworkException.Unknown(error)
        }
    }

    private fun parseErrorBody(body: ResponseBody?): NetworkEntityError {
        return if (body != null) {
            try {
                val rawErrorFromServer = moshi.fromJsonOrThrow(NetworkEntityError::class, body.string())
                rawErrorFromServer.convertToHumanReadableError()
            } catch (nonValidJsonOrNull: IOException) {
                Timber.tag(NETWORK_LOG_TAG).d(nonValidJsonOrNull)
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
