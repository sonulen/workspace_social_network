private object TestVersions {
    const val JUNIT = "4.13.2"
    const val ANDROIDX = "2.1.0"
    const val KOTEST = "4.6.0"
    const val MOCKK = "1.11.0"
    const val KOTLIN_COROUTINES_TEST = "1.5.0"
}

object TestDependency {
    const val KOTLIN_COROUTINES_TEST =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${TestVersions.KOTLIN_COROUTINES_TEST}"
    const val JUNIT = "junit:junit:${TestVersions.JUNIT}"
    const val ANDROIDX = "androidx.arch.core:core-testing:${TestVersions.ANDROIDX}"
    const val KOTEST_RUNNER = "io.kotest:kotest-runner-junit5:${TestVersions.KOTEST}"
    const val KOTEST_ASSERTION = "io.kotest:kotest-assertions-core:${TestVersions.KOTEST}"
    const val KOTEST_DATATEST = "io.kotest:kotest-framework-datatest:${TestVersions.KOTEST}"
    const val MOCKK = "io.mockk:mockk:${TestVersions.MOCKK}"
}
