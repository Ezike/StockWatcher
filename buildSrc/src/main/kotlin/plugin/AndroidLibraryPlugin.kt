package plugin

import Library
import androidModule
import daggerHilt
import extensions.AndroidLib
import extensions.PluginExtension
import implementation
import kapt
import kotlinAndroid
import kotlinKapt
import testImplementation

class AndroidLibraryPlugin : BasePlugin(
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
        testImplementation(
            Library.junit,
            Library.mockito,
            Library.mockitoInline,
            Library.junitParams,
            Library.coroutinesTest,
            Library.turbine
        )
        kapt(Library.daggerHiltCompiler)
    },
    extensions = arrayOf(PluginExtension.AndroidLib),
)