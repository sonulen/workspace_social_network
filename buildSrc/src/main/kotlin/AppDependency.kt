internal object AppVersions {
    // UI
    const val CORE = "1.3.0"
    const val APPCOMPAT = "1.2.0"
    const val BROWSER = "1.3.0"
    const val CONSTRAINT_LAYOUT = "2.0.4"
    const val LIFECYCLE = "2.3.1"
    const val MATERIAL_UI = "1.3.0"
    const val RMR_VIEW_BINDING = "4.1.2-2"
    const val RMR_LIVEDATA = "2.3.0-0"
    const val RMR_INPUT_MASK = "6.0.0"

    const val NAVIGATION = "2.3.5"

    const val OK_HTTP_LOGGING = "4.9.1"

    const val MAP_MEMORY = "2.0"
}

object AppDependency {
    // UI
    const val CORE = "androidx.core:core-ktx:${AppVersions.CORE}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${AppVersions.APPCOMPAT}"
    const val BROWSER = "androidx.browser:browser:${AppVersions.BROWSER}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${AppVersions.CONSTRAINT_LAYOUT}"
    const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${AppVersions.LIFECYCLE}"
    const val MATERIAL_UI = "com.google.android.material:material:${AppVersions.MATERIAL_UI}"
    const val RMR_VIEW_BINDING = "com.redmadrobot.extensions:viewbinding-ktx:${AppVersions.RMR_VIEW_BINDING}"
    const val RMR_LIVEDATA = "com.redmadrobot.extensions:lifecycle-livedata-ktx:${AppVersions.RMR_LIVEDATA}"
    const val RMR_INPUT_MASK = "com.redmadrobot:input-mask-android:${AppVersions.RMR_INPUT_MASK}"

    // NAVIGATION
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment:${AppVersions.NAVIGATION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui:${AppVersions.NAVIGATION}"
    const val NAVIGATION_FRAGMENT_KTX =
        "androidx.navigation:navigation-fragment-ktx:${AppVersions.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${AppVersions.NAVIGATION}"

    // DAGGER
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${CoreVersions.DAGGER}"
    const val OK_HTTP_LOGGING = "com.squareup.okhttp3:logging-interceptor:${AppVersions.OK_HTTP_LOGGING}"

    // MapMemmory
    const val MAPMEMORY = "com.redmadrobot.mapmemory:mapmemory:${AppVersions.MAP_MEMORY}"
    const val MAPMEMORY_COROUTINES = "com.redmadrobot.mapmemory:mapmemory-coroutines:${AppVersions.MAP_MEMORY}"
}
