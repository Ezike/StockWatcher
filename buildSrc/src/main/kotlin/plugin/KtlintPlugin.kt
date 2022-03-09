package plugin

import extensions.Ktlint
import extensions.ProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class KtlintPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.apply(KTLINT_PLUGIN_ID)
        val extension = ProjectExtension.Ktlint
        val ktlintExtension = project.extensions.getByName(extension.name)
        extension.configure(ktlintExtension)
    }

    private companion object {
        const val KTLINT_PLUGIN_ID: String = "org.jlleitschuh.gradle.ktlint"
    }
}
