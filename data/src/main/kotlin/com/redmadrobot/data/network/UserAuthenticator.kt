package com.redmadrobot.data.network

import com.redmadrobot.domain.repository.AuthRepository
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
) : Authenticator {
    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TAG = "OkHttp"
    }

    @Synchronized
    override fun authenticate(route: Route?, response: Response): Request? {
        val currentAccessToken = session.getAccessToken()
        if (currentAccessToken == null) {
            Timber.tag(TAG).d("Попали в authenticate не имея Access Token")
        }
        // Получаем текущий токен из запроса
        val requestAccessToken =
            response.request.header(HEADER_AUTHORIZATION)?.removePrefix(AuthRepository.HEADER_TOKEN_PREFIX)

        if (currentAccessToken == null) {
            Timber.tag(TAG).d("В header запроса нет токена")
        }

        return if (currentAccessToken == requestAccessToken) {
            Timber.tag(TAG).i("Token refreshing")
            val newAccessToken = refreshTokenSynchronously()
            newAccessToken?.let { buildRequestWithNewAccessToken(response, it) }
        } else {
            Timber.tag(TAG).i("Proceeding with current token")
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
                repository.refresh().refresh
            } catch (exception: Exception) {
                Timber.tag(TAG).d(exception, "An error occurred while token updating")
                // Тут вызываем какой-то метод репозитория, который кинет событие, что нужна деавторизация
                // TODO repository.logout(tokenExpired = true).launchIn(this)
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
