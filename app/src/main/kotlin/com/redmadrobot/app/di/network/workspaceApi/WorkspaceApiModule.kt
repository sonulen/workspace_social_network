package com.redmadrobot.app.di.network.workspaceApi

import com.redmadrobot.app.di.network.NetworkModule
import com.redmadrobot.app.di.qualifiers.AuthorizedZone
import com.redmadrobot.data.network.NetworkRouter
import com.redmadrobot.data.network.UserAuthenticator
import com.redmadrobot.data.network.workspace.WorkspaceApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object WorkspaceApiModule {
    @Provides
    @Singleton
    @AuthorizedZone
    fun provideAuthorizedOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        errorInterceptor: Interceptor,
        authenticator: UserAuthenticator,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(errorInterceptor)
            .authenticator(authenticator)
            .callTimeout(NetworkModule.HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(NetworkModule.HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkModule.HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkModule.HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
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
