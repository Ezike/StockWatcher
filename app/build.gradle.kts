plugins {
    androidApp
}

android.viewBinding.isEnabled = true

dependencies {
    implementation(
        project(Project.libStockPrice)
    )
    implementation(
        Library.coroutines,
        Library.viewModel,
        Library.activity,
        Library.hiltViewModel,
        Library.lifecycleRuntime,
        Library.recyclerView
    )
    kapt(Library.androidxHiltCompiler)
}
