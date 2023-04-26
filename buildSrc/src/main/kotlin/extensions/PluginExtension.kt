package extensions

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

interface PluginExtension {
    val name: String
    fun configure(extension: Any)

    companion object
}

val PluginExtension.Companion.Java
    get() = extension<JavaPluginExtension>("java") {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

val PluginExtension.Companion.Kotlin
    get() = extension<KotlinJvmProjectExtension>("kotlin") {
        explicitApi()
    }

val PluginExtension.Companion.AndroidLib
    get() = extension<LibraryExtension>("android") {
        PluginExtension.FeatureModule.configure(this)
        PluginExtension.KotlinJvmExtension.config(
            (this as ExtensionAware).extensions
        )
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

val PluginExtension.Companion.KotlinJvmExtension
    get() = extension<KotlinJvmOptions>("kotlinOptions") {
        freeCompilerArgs += "-Xexplicit-api=strict"
        jvmTarget = "17"
    }
