package com.redmadrobot.app.di.network

import com.redmadrobot.data.network.AuthApi
import com.redmadrobot.data.network.NetworkRouter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
    fun provideAuthApiClient(
        okHttpClient: OkHttpClient,
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

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}
