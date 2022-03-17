package extensions

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

interface ProjectExtension {
    val name: String
    fun configure(extension: Any)

    companion object
}

val ProjectExtension.Companion.Java
    get() = extension<JavaPluginExtension>("java") {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

val ProjectExtension.Companion.Kotlin
    get() = extension<KotlinJvmProjectExtension>("kotlin") {
        explicitApi()
    }

val ProjectExtension.Companion.AndroidLib
    get() = extension<LibraryExtension>("android") {
        ProjectExtension.FeatureModule.configure(this)
        ProjectExtension.KotlinJvmExtension.config(
            (this as ExtensionAware).extensions
        )
    }

val ProjectExtension.Companion.KotlinJvmExtension
    get() = extension<KotlinJvmOptions>("kotlinOptions") {
        freeCompilerArgs += "-Xexplicit-api=strict"
    }
