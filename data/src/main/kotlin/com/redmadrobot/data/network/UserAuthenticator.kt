package com.redmadrobot.data.network

import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.repository.DeauthorizationRepository
import com.redmadrobot.domain.repository.SessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class UserAuthenticator @Inject constructor(
    private val repository: AuthRepository,
    private val session: SessionRepository,
    private val deauthorizationRepository: DeauthorizationRepository,
) : Authenticator {
    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TAG = "OkHttp"
    }

    @Synchronized
    override fun authenticate(route: Route?, response: Response): Request? {
        val currentAccessToken = session.getAccessToken()
        // Получаем текущий токен из запроса
        val requestAccessToken =
            response.request.header(HEADER_AUTHORIZATION)?.removePrefix(AuthRepository.HEADER_TOKEN_PREFIX)

        return if (currentAccessToken == requestAccessToken) {
            val newAccessToken = refreshTokenSynchronously()
            newAccessToken?.let { buildRequestWithNewAccessToken(response, it) }
        } else {
            currentAccessToken?.let {
                buildRequestWithNewAccessToken(response, currentAccessToken)
            }
        }
    }

    @Suppress("TooGenericExceptionCaught") // Хотим обезопаситься от любых исключений
    private fun refreshTokenSynchronously(): String? {
        return runBlocking {
            try {
                // Пробуем запросить новый токен
                repository.refresh().access
            } catch (exception: Exception) {
                Timber.tag(TAG).d(exception, "An error occurred while token updating")
                // Вызываем метод репозитория, который кинет событие, что нужна деавторизация
                deauthorizationRepository.logout("Что-то пошло не так. Давай начнем сначала?")
                // Если ошибка - возвращаем null, это значит что больше не надо пытаться авторизоваться
                null
            }
        }
    }

    private fun buildRequestWithNewAccessToken(response: Response, newAccessToken: String): Request {
        return response.request
            .newBuilder()
            .header(
                HEADER_AUTHORIZATION,
                AuthRepository.HEADER_TOKEN_PREFIX + newAccessToken
            ).build()
    }
}
