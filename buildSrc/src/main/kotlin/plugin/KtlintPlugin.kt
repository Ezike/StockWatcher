package plugin

import Version
import extensions.PluginExtension
import extensions.extension
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class KtlintPlugin : BasePlugin(
    plugins = { apply("org.jlleitschuh.gradle.ktlint") },
    dependencies = {},
    extensions = arrayOf(KtlintExtension)
)

private val KtlintExtension: PluginExtension
    get() = extension<KtlintExtension>("ktlint") {
        version.set(Version.ktlint)
        outputToConsole.set(true)
        disabledRules.add("import-ordering")
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }
