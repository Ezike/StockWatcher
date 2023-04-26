package extensions

import AppConfig
import BuildType.Companion.Debug
import com.android.build.gradle.AppExtension
import org.gradle.api.JavaVersion

val PluginExtension.Companion.AndroidApp
    get() = extension<AppExtension>("android") {
        defaultConfig {
            applicationId = AppConfig.applicationId
            compileSdkVersion(AppConfig.compileSdkVersion)
            targetSdk = AppConfig.targetSdkVersion
            minSdk = AppConfig.minSdkVersion
            versionCode(AppConfig.versionCode)
            versionName(AppConfig.versionName)
            testInstrumentationRunner = AppConfig.testInstrumentationRunner
        }

        buildTypes {
            named(Debug.name) {
                applicationIdSuffix = Debug.applicationIdSuffix
                versionNameSuffix = Debug.versionNameSuffix

                isMinifyEnabled = Debug.isMinifyEnabled
                isTestCoverageEnabled = Debug.isTestCoverageEnabled

                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
