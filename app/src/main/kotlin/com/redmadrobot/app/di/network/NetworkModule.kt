package com.redmadrobot.app.di.network

import com.redmadrobot.app.di.qualifiers.UnauthorizedZone
import com.redmadrobot.data.network.AuthApi
import com.redmadrobot.data.network.NetworkRouter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkModule {
    private const val HTTP_CLIENT_TIMEOUT = 30L

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
    @UnauthorizedZone
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiClient(
        @UnauthorizedZone okHttpClient: OkHttpClient,
        moshiFactory: MoshiConverterFactory,
    ): AuthApi {
        val baseUrl = "https://" + NetworkRouter.BASE_HOSTNAME
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(moshiFactory)
            .build()
            .create(AuthApi::class.java)
    }
}
