package extensions

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

val ProjectExtension.Companion.KotlinJvmExtension: ProjectExtension
    get() = KotlinJvmExtension()

private class KotlinJvmExtension : ProjectExtension {

    override val name: String get() = "kotlinOptions"

    override fun configure(extension: Any) {
        if (extension !is KotlinJvmOptions) return
        extension.apply {
            freeCompilerArgs += "-Xexplicit-api=strict"
        }
    }
}
