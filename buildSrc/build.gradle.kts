import Build_gradle.Plugin.androidLib
import Build_gradle.Plugin.app
import Build_gradle.Plugin.featureModule
import Build_gradle.Plugin.kotlinLib
import Build_gradle.Plugin.ktlintPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register(app) {
            id = app
            implementationClass = "plugin.ApplicationPlugin"
        }

        register(androidLib) {
            id = androidLib
            implementationClass = "plugin.AndroidLibraryPlugin"
        }

        register(featureModule) {
            id = featureModule
            implementationClass = "plugin.FeatureModulePlugin"
        }

        register(kotlinLib) {
            id = kotlinLib
            implementationClass = "plugin.KotlinLibraryPlugin"
        }

        register(ktlintPlugin) {
            id = ktlintPlugin
            implementationClass = "plugin.KtlintPlugin"
        }
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = Plugin.Version.kotlin
}

dependencies {
    implementation(Plugin.kotlin)
    implementation(Plugin.androidGradle)
    implementation(Plugin.daggerHilt)
    implementation(Plugin.ktlint)
}

repositories {
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
}

object Plugin {
    const val app: String = "app"
    const val androidLib: String = "androidLibrary"
    const val featureModule: String = "featureModule"
    const val kotlinLib: String = "kotlinLibrary"
    const val ktlintPlugin: String = "ktlint"

    object Version {
        const val kotlin: String = "1.7.20"
        const val androidGradle = "7.3.0"
        const val daggerHilt = "2.44"
        const val ktlint = "10.2.1"
    }

    const val kotlin: String = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val androidGradle: String = "com.android.tools.build:gradle:${Version.androidGradle}"
    const val daggerHilt: String =
        "com.google.dagger:hilt-android-gradle-plugin:${Version.daggerHilt}"
    const val ktlint: String = "org.jlleitschuh.gradle:ktlint-gradle:${Version.ktlint}"
}
