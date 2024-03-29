package extensions

import org.gradle.api.plugins.ExtensionContainer

fun PluginExtension.config(extensionContainer: ExtensionContainer) {
    configure(extensionContainer.getByName(name))
}

inline fun <reified T> Any.asType(action: T.() -> Unit) {
    if (this is T) {
        action(this)
    }
}

inline fun <reified T> extension(
    name: String,
    crossinline config: T.() -> Unit
) = object : PluginExtension {
    override val name: String
        get() = name

    override fun configure(extension: Any) {
        extension.asType(config)
    }
}
