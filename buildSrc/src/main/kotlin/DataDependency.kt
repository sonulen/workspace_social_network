private object DataVersions {
    const val OKHTTP_LOGGING = "3.1.0"
    const val MAP_MEMORY = "2.0"
}

object DataDependency {
    const val OKHTTP_LOGGING = "com.github.ihsanbal:LoggingInterceptor:${DataVersions.OKHTTP_LOGGING}"

    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${CoreVersions.DAGGER}"
    const val MOSHI_COMPILER = "com.squareup.moshi:moshi-kotlin-codegen:${CoreVersions.MOSHI}"
    const val KOTLINX = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${CoreVersions.KOTLINX}"

    // MapMemmory
    const val MAPMEMORY = "com.redmadrobot.mapmemory:mapmemory:${DataVersions.MAP_MEMORY}"
    const val MAPMEMORY_COROUTINES = "com.redmadrobot.mapmemory:mapmemory-coroutines:${DataVersions.MAP_MEMORY}"
}
