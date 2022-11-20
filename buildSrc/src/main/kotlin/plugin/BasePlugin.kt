package plugin

import extensions.PluginExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer

typealias PluginConfig = PluginContainer.() -> Unit
typealias DependencyConfig = DependencyHandler.() -> Unit

/**
 * @param plugins: add gradle plugins
 * @param dependencies: add dependencies for project
 * @param extensions: configuration for the gradle plugins applied in [plugins]
 */
abstract class BasePlugin(
    private val plugins: PluginConfig,
    private val dependencies: DependencyConfig,
    private val extensions: Array<PluginExtension> = emptyArray(),
) : Plugin<Project> {

    override fun apply(target: Project) {
        plugins(target.plugins)
        addExtensions(target)
        dependencies(target.dependencies)
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
