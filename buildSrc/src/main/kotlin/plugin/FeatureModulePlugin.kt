package plugin

import Library
import androidModule
import daggerHilt
import extensions.FeatureModule
import extensions.PluginExtension
import implementation
import kapt
import kotlinAndroid
import kotlinKapt

class FeatureModulePlugin : BasePlugin(
    plugins = {
        androidModule
        kotlinAndroid
        kotlinKapt
        daggerHilt
    },
    dependencies = {
        implementation(
            Library.daggerHiltAndroid,
            Library.coroutines
        )
        kapt(Library.daggerHiltCompiler)
    },
    extensions = arrayOf(PluginExtension.FeatureModule)
)