package com.redmadrobot.app.di.network

import android.net.ConnectivityManager
import com.redmadrobot.data.network.errors.NetworkErrorHandler
import com.redmadrobot.data.network.errors.NetworkErrorInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

@Module
object NetworkModule {
    const val HTTP_CLIENT_TIMEOUT = 30L

    @Provides
    @Reusable
    fun provideMoshi(): Moshi = Moshi.Builder()
        .build()

    @Provides
    @Reusable
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    @Provides
    @Reusable
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
            redactHeader("Authorization")
            redactHeader("Cookie")
        }
    }

    @Provides
    @Reusable
    fun provideErrorInterceptor(
        errorHandler: NetworkErrorHandler,
        connectivityManager: ConnectivityManager,
    ): Interceptor = NetworkErrorInterceptor(errorHandler, connectivityManager)
}
