package plugin

import Library
import androidModule
import daggerHilt
import extensions.FeatureModule
import extensions.ProjectExtension
import implementation
import kapt
import kotlinAndroid
import kotlinKapt

class FeatureModulePlugin : BasePlugin() {

    override val pluginConfig: PluginConfig
        get() = {
            androidModule
            kotlinAndroid
            kotlinKapt
            daggerHilt
        }

    override val libraryConfig: LibraryConfig
        get() = {
            implementation(
                Library.daggerHiltAndroid,
                Library.coroutines
            )
            kapt(Library.daggerHiltCompiler)
        }

    override val extensions: Array<ProjectExtension>
        get() = arrayOf(ProjectExtension.FeatureModule)
}
