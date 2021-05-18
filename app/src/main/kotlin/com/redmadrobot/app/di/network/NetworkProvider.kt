package com.redmadrobot.app.di.network

import com.squareup.moshi.Moshi

interface NetworkProvider {
    fun moshi(): Moshi
}
