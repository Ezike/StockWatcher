import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.apply
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

// region internal plugin extensions

internal val PluginContainer.kotlinAndroid: Unit
    get() {
        apply("kotlin-android")
    }

internal val PluginContainer.androidModule: Unit
    get() {
        apply("com.android.library")
    }

internal val PluginContainer.kotlinKapt: Unit
    get() {
        apply("kotlin-kapt")
    }

internal val PluginContainer.daggerHilt: Unit
    get() {
        apply("dagger.hilt.android.plugin")
    }

// endregion

// region external plugin extensions

val PluginDependenciesSpec.androidApp: PluginDependencySpec
    get() = id("app")

val PluginDependenciesSpec.androidLib: PluginDependencySpec
    get() = id("androidLibrary")

val PluginDependenciesSpec.featureModule: PluginDependencySpec
    get() = id("featureModule")

val PluginDependenciesSpec.kotlinLib: PluginDependencySpec
    get() = id("kotlinLibrary")

val PluginDependenciesSpec.ktlint: PluginDependencySpec
    get() = id("ktlint")

// endregion

// region project extensions

val Project.applyKtlint
    get() = apply(plugin = "ktlint")

//endregion

// region dependency extensions

fun DependencyHandler.implementation(dependency: Any) = add(
    "implementation", dependency
)

fun DependencyHandler.implementation(vararg dependencies: Any) {
    dependencies.forEach(::implementation)
}

fun DependencyHandler.testImplementation(dependency: Any) = add(
    "testImplementation", dependency
)

fun DependencyHandler.testImplementation(vararg dependencies: Any) {
    dependencies.forEach(::testImplementation)
}

fun DependencyHandler.kapt(dependency: Any): Dependency? = add(
    "kapt", dependency
)

fun DependencyHandler.kapt(vararg dependencies: Any) {
    dependencies.forEach(::kapt)
}

// endregion
