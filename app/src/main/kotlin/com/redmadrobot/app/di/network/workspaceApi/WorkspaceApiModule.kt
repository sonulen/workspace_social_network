package com.redmadrobot.app.di.network.workspaceApi

import com.redmadrobot.app.BuildConfig
import com.redmadrobot.app.di.network.NetworkModule
import com.redmadrobot.app.di.qualifiers.AuthorizedZone
import com.redmadrobot.data.network.AuthInterceptor
import com.redmadrobot.data.network.NetworkRouter
import com.redmadrobot.data.network.UserAuthenticator
import com.redmadrobot.data.network.workspace.WorkspaceApi
import com.redmadrobot.domain.repository.SessionRepository
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
object WorkspaceApiModule {
    @Provides
    fun provideAuthInterceptor(sessionRepository: SessionRepository): AuthInterceptor =
        AuthInterceptor(sessionRepository)

    @Provides
    @AuthorizedZone
    fun provideAuthorizedOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        errorInterceptor: Interceptor,
        authInterceptor: AuthInterceptor,
        authenticator: UserAuthenticator,
        certificatePinner: CertificatePinner,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(errorInterceptor)
            .authenticator(authenticator)
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
    fun provideWorkspaceApiClient(
        @AuthorizedZone okHttpClient: OkHttpClient,
        moshiFactory: MoshiConverterFactory,
    ): WorkspaceApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(NetworkRouter.BASE_URL)
        .addConverterFactory(moshiFactory)
        .build()
        .create(WorkspaceApi::class.java)
}
