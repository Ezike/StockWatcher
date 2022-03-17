package extensions

import AppConfig
import BuildType.Companion.Debug
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion

val ProjectExtension.Companion.FeatureModule
    get() = extension<LibraryExtension>("android") {
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
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }
