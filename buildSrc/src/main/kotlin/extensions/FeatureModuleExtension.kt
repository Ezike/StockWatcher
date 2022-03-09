package extensions

import AppConfig
import BuildType.Companion.Debug
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion

val ProjectExtension.Companion.FeatureModule: ProjectExtension
    get() = FeatureModuleExtension()

private class FeatureModuleExtension : ProjectExtension {

    override val name: String get() = "android"

    override fun configure(extension: Any) {
        if (extension !is LibraryExtension) return
        extension.apply {
            androidConfig()
        }
    }
}

fun LibraryExtension.androidConfig() {
    defaultConfig {
        targetSdk = AppConfig.targetSdkVersion
        minSdk = AppConfig.minSdkVersion
        compileSdk = AppConfig.compileSdkVersion
        consumerProguardFiles("proguard-rules.pro")
    }

    buildTypes {
        named(Debug.name) {
            isMinifyEnabled = Debug.isMinifyEnabled
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
