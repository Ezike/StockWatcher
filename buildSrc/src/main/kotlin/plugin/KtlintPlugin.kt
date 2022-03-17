package plugin

import extensions.extension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class KtlintPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.apply(KTLINT_PLUGIN_ID)
        val extension = ktlintExtension()
        val ktlintExtension = project.extensions.getByName(extension.name)
        extension.configure(ktlintExtension)
    }

    private fun ktlintExtension() = extension<KtlintExtension>("ktlint") {
        version.set(Version.ktlint)
        outputToConsole.set(true)
        disabledRules.add("import-ordering")
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    private companion object {
        const val KTLINT_PLUGIN_ID: String = "org.jlleitschuh.gradle.ktlint"
    }
}
