package com.redmadrobot.app.di.network.authApi

import com.redmadrobot.app.BuildConfig
import com.redmadrobot.app.di.network.NetworkModule
import com.redmadrobot.app.di.qualifiers.UnauthorizedZone
import com.redmadrobot.data.network.NetworkRouter
import com.redmadrobot.data.network.auth.AuthApi
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
object AuthApiModule {
    @Provides
    @UnauthorizedZone
    fun provideUnauthorizedOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        errorInterceptor: Interceptor,
        certificatePinner: CertificatePinner,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(errorInterceptor)
            .callTimeout(NetworkModule.HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(NetworkModule.HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkModule.HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkModule.HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)

        if (!BuildConfig.DEBUG) {
            builder.certificatePinner(certificatePinner)
        }
        return builder.build()
    }

    @Provides
    fun provideAuthApiClient(
        @UnauthorizedZone okHttpClient: OkHttpClient,
        moshiFactory: MoshiConverterFactory,
    ): AuthApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(NetworkRouter.BASE_URL)
        .addConverterFactory(moshiFactory)
        .build()
        .create(AuthApi::class.java)
}
