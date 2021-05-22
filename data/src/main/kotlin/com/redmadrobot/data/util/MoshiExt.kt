package com.redmadrobot.data.util

import com.squareup.moshi.Moshi
import java.io.IOException
import kotlin.reflect.KClass

@Throws(IOException::class)
fun <T : Any> Moshi.fromJsonOrThrow(type: KClass<T>, content: String): T =
    adapter(type.java).fromJson(content) ?: throw IOException("Moshi adapter($type) with data ($content) returned null")
