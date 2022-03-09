package extensions

import com.android.build.gradle.LibraryExtension
import org.gradle.api.plugins.ExtensionAware

val ProjectExtension.Companion.AndroidLib: ProjectExtension
    get() = AndroidLibExtension()

private class AndroidLibExtension : ProjectExtension {

    override val name: String get() = "android"

    override fun configure(extension: Any) {
        if (extension !is LibraryExtension) return
        extension.apply {
            androidConfig()
            ProjectExtension.KotlinJvmExtension.config(
                (this as ExtensionAware).extensions
            )
        }
    }
}
