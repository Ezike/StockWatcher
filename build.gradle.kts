import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    ktlint
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects project@{
    applyKtlint
    tasks.withType<KotlinCompile>().configureEach {
        with(kotlinOptions) {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            freeCompilerArgs += "-Xuse-experimental=" +
                "kotlin.Experimental," +
                "kotlinx.coroutines.ExperimentalCoroutinesApi," +
                "kotlinx.coroutines.InternalCoroutinesApi," +
                "kotlinx.coroutines.ObsoleteCoroutinesApi," +
                "kotlinx.coroutines.FlowPreview"
            freeCompilerArgs += "-Xopt-in=kotlin.ExperimentalStdlibApi"
        }
    }

    tasks.register("runTests") {
        finalizedBy("${this@project.path}:testDebugUnitTest")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
