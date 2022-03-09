package plugin

import extensions.ProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer

typealias PluginConfig = PluginContainer.() -> Unit
typealias LibraryConfig = DependencyHandler.() -> Unit

abstract class BasePlugin : Plugin<Project> {

    protected abstract val pluginConfig: PluginConfig
    protected abstract val libraryConfig: LibraryConfig
    protected abstract val extensions: Array<ProjectExtension>

    override fun apply(target: Project) {
        pluginConfig(target.plugins)
        addExtensions(target)
        libraryConfig(target.dependencies)
    }

    private fun addExtensions(project: Project) {
        extensions.forEach { extension ->
            val extensionType = project.extensions.getByName(
                extension.name
            )
            extension.configure(extensionType)
        }
    }
}
