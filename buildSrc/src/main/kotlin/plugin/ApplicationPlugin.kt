package plugin

import Library
import daggerHilt
import extensions.AndroidApp
import extensions.ProjectExtension
import implementation
import kapt
import kotlinAndroid
import kotlinKapt
import testImplementation

class ApplicationPlugin : BasePlugin() {

    override val pluginConfig: PluginConfig
        get() = {
            apply(APP_PLUGIN_ID)
            kotlinAndroid
            kotlinKapt
            daggerHilt
        }

    override val libraryConfig: LibraryConfig
        get() = {
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
        }

    override val extensions: Array<ProjectExtension>
        get() = arrayOf(ProjectExtension.AndroidApp)

    private companion object {
        const val APP_PLUGIN_ID: String = "com.android.application"
    }
}
