package com.redmadrobot.app.di.network

import com.squareup.moshi.Moshi
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory

interface NetworkProvider {
    fun moshi(): Moshi
    fun moshiConverterFactory(): MoshiConverterFactory
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor
    fun provideErrorInterceptor(): Interceptor
    fun provideCertificatePinner(): CertificatePinner
}
