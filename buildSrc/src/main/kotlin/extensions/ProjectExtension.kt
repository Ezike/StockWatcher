package extensions

import org.gradle.api.plugins.ExtensionContainer

interface ProjectExtension {
    val name: String
    fun configure(extension: Any)

    companion object
}

fun ProjectExtension.config(extensionContainer: ExtensionContainer) {
    configure(extensionContainer.getByName(name))
}
