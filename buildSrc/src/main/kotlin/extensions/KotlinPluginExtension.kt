package extensions

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

val ProjectExtension.Companion.KotlinExtension: ProjectExtension
    get() = KotlinPluginExtension()

private class KotlinPluginExtension : ProjectExtension {

    override val name: String get() = "kotlin"

    override fun configure(extension: Any) {
        if (extension !is KotlinJvmProjectExtension) return
        extension.apply {
            explicitApi()
        }
    }
}
