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
        Library.lifecycleRuntime,
        Library.recyclerView
    )
}
