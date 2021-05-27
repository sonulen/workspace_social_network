package com.redmadrobot.app.di.network

import android.net.ConnectivityManager
import com.redmadrobot.data.network.errors.NetworkErrorHandler
import com.redmadrobot.data.network.errors.NetworkErrorInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
object NetworkModule {
    const val HTTP_CLIENT_TIMEOUT = 30L

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor {
            fun log(message: String) {
                Timber.tag("OkHttp").d(message)
            }
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
            redactHeader("Authorization")
            redactHeader("Cookie")
        }
    }

    @Provides
    @Singleton
    fun provideErrorInterceptor(
        errorHandler: NetworkErrorHandler,
        connectivityManager: ConnectivityManager,
    ): Interceptor = NetworkErrorInterceptor(errorHandler, connectivityManager)
}
