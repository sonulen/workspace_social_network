package com.redmadrobot.data.util

import okhttp3.Response
import okhttp3.ResponseBody

fun Response.bodyCopy(): ResponseBody? = body?.let { peekBody(it.contentLength()) }
