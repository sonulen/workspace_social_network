package com.redmadrobot.data.network

import okio.IOException

class NetworkException(override val message: String, val code: String) : IOException()
