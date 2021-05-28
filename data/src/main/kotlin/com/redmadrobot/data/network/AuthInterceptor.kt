package com.redmadrobot.data.network

import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.repository.SessionRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionRepository: SessionRepository,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        sessionRepository.getAccessToken()?.let {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "${AuthRepository.HEADER_TOKEN_PREFIX} $it").build()
            return chain.proceed(request = request)
        }
        return chain.proceed(chain.request())
    }
}
