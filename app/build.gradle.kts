plugins {
    androidApp
}

android {
    namespace = "com.ezike.tobenna.stockwatcher"
    viewBinding.isEnabled = true
}

dependencies {
    implementation(project(Project.libStockPrice))
    implementation(
        Library.coroutines,
        Library.viewModel,
        Library.activity,
        Library.lifecycleRuntime,
        Library.recyclerView
    )
}
