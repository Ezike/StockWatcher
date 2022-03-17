package plugin

import Library
import extensions.Java
import extensions.Kotlin
import extensions.ProjectExtension
import implementation
import kapt
import kotlinKapt

class KotlinLibraryPlugin : BasePlugin() {
    override val pluginConfig: PluginConfig
        get() = {
            apply(KOTLIN_PLUGIN_ID)
            kotlinKapt
        }

    override val libraryConfig: LibraryConfig
        get() = {
            implementation(
                Library.hiltCore,
                Library.coroutines
            )
            kapt(Library.daggerHiltCompiler)
        }

    override val extensions: Array<ProjectExtension>
        get() = arrayOf(
            ProjectExtension.Kotlin,
            ProjectExtension.Java
        )

    private companion object {
        const val KOTLIN_PLUGIN_ID: String = "kotlin"
    }
}
