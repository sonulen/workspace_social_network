private object DomainVersions {
    const val RETROFIT = "2.9.0"
    const val CRYPTO = "1.2.2"
    const val TIMBER = "4.7.1"
    const val LOGGER = "2.2.0"
}

object DomainDependency {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${CoreVersions.KOTLIN}"
    const val DAGGER = "com.google.dagger:dagger:${CoreVersions.DAGGER}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${DomainVersions.RETROFIT}"
    const val RETROFIT_MOSHI = "com.squareup.retrofit2:converter-moshi:${DomainVersions.RETROFIT}"
    const val MOSHI = "com.squareup.moshi:moshi:${CoreVersions.MOSHI}"
    const val MOSHI_COMPILER = "com.squareup.moshi:moshi-kotlin-codegen:${CoreVersions.MOSHI}"
    const val MOSHI_ADAPTERS = "com.squareup.moshi:moshi-adapters:${CoreVersions.MOSHI}"
    const val CRYPTO = "com.google.crypto.tink:tink-android:${DomainVersions.CRYPTO}"
    const val TIMBER = "com.jakewharton.timber:timber:${DomainVersions.TIMBER}"
    const val LOGGER = "com.orhanobut:logger:${DomainVersions.LOGGER}"
}
