package plugin

import Library
import extensions.Java
import extensions.Kotlin
import extensions.PluginExtension
import implementation
import kapt
import kotlinKapt

class KotlinLibraryPlugin : BasePlugin(
    plugins = {
        apply("kotlin")
        kotlinKapt
    },
    dependencies = {
        implementation(
            Library.hiltCore,
            Library.coroutines
        )
        kapt(Library.daggerHiltCompiler)
    },
    extensions = arrayOf(PluginExtension.Kotlin, PluginExtension.Java),
)
