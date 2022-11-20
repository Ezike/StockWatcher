package plugin

import Library
import daggerHilt
import extensions.AndroidApp
import extensions.PluginExtension
import implementation
import kapt
import kotlinAndroid
import kotlinKapt
import testImplementation

class ApplicationPlugin : BasePlugin(
    plugins = {
        apply("com.android.application")
        kotlinAndroid
        kotlinKapt
        daggerHilt
    },
    dependencies = {
        implementation(
            Library.coreKtx,
            Library.appCompat,
            Library.material,
            Library.daggerHiltAndroid
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
    extensions = arrayOf(PluginExtension.AndroidApp),
)