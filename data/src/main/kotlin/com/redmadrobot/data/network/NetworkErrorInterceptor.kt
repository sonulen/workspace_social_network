package com.redmadrobot.data.network

import com.redmadrobot.data.entity.api.NetworkEntityError
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
import timber.log.Timber

class NetworkErrorInterceptor(private val moshi: Moshi) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (!response.isSuccessful) {
            val responseBody = response.body?.string()
            if (responseBody != null) {
                val adapter: JsonAdapter<NetworkEntityError> = moshi.adapter(NetworkEntityError::class.java)
                val entityError = adapter.fromJson(responseBody)

                if (entityError != null) {
                    throw NetworkException(entityError.message, entityError.code)
                }
            }
            Timber.d("От сервера пришел ответ с ошибкой не конвертируемой в NetworkEntityError")
            throw IOException("От сервера получен странный ответ")
        }

        return response
    }
}
