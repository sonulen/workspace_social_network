package com.redmadrobot.data.network.errors

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import com.redmadrobot.data.util.hasConnection
import okhttp3.Interceptor
import okhttp3.Response

class NetworkErrorInterceptor(
    private val errorHandler: NetworkErrorHandler,
    private val connectivityManager: ConnectivityManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw errorHandler.noInternetAccessException
        }

        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            throw errorHandler.networkErrorToThrow(response)
        }

        return response
    }

    @SuppressLint("MissingPermission")
    private fun isConnected(): Boolean {
        val activeNetwork = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return activeNetwork?.hasConnection() ?: false
    }
}
