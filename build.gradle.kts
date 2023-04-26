import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    ktlint
}

subprojects project@{
    applyKtlint
    tasks.withType<KotlinCompile>().configureEach {
        with(kotlinOptions) {
            jvmTarget = JavaVersion.VERSION_17.toString()
            freeCompilerArgs += "-Xuse-experimental=" +
                "kotlin.Experimental," +
                "kotlinx.coroutines.ExperimentalCoroutinesApi," +
                "kotlinx.coroutines.InternalCoroutinesApi," +
                "kotlinx.coroutines.ObsoleteCoroutinesApi," +
                "kotlinx.coroutines.FlowPreview"
            freeCompilerArgs += "-Xopt-in=kotlin.ExperimentalStdlibApi"
            freeCompilerArgs += "-Xcontext-receivers"
        }
    }

    tasks.register("runTests") {
        finalizedBy("${this@project.path}:testDebugUnitTest")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
