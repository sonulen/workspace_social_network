package com.redmadrobot.data.util

import android.net.NetworkCapabilities

fun NetworkCapabilities.hasConnection(): Boolean {
    return hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) or
        hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}
