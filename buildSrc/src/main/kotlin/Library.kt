object Library {
    // AndroidX
    const val coreKtx: String = "androidx.core:core-ktx:${Version.coreKtx}"
    const val appCompat: String = "androidx.appcompat:appcompat:${Version.appCompat}"
    const val activity = "androidx.activity:activity-ktx:${Version.activity}"
    const val recyclerView: String = "androidx.recyclerview:recyclerview:${Version.recyclerView}"
    const val viewModel: String =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycle}"

    // Design
    const val material: String = "com.google.android.material:material:${Version.material}"

    // DI
    const val daggerHiltAndroid: String = "com.google.dagger:hilt-android:${Version.daggerHilt}"
    const val hiltCore: String = "com.google.dagger:hilt-core:${Version.daggerHilt}"
    const val daggerHiltCompiler: String =
        "com.google.dagger:hilt-android-compiler:${Version.daggerHilt}"

    // coroutines
    const val coroutines: String =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"

    // Coroutine test
    const val coroutinesTest: String =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutines}"

    // Square
    const val okhttpLogger: String =
        "com.squareup.okhttp3:logging-interceptor:${Version.okhttp}"
    const val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Version.moshi}"

    // tinder scarlet
    const val scarlet = "com.tinder.scarlet:scarlet:0.1.12"
    const val scarletLifecycle = "com.tinder.scarlet:lifecycle-android:${Version.scarlet}"
    const val scarletOkhttp = "com.tinder.scarlet:websocket-okhttp:${Version.scarlet}"
    const val scarletMoshi = "com.tinder.scarlet:message-adapter-moshi:${Version.scarlet}"

    // Junit
    const val junit: String = "junit:junit:${Version.junit}"

    // Mockito
    const val mockito = "org.mockito.kotlin:mockito-kotlin:${Version.mockito}"
    const val mockitoInline = "org.mockito:mockito-inline:${Version.mockitoInline}"
    const val junitParams = "pl.pragmatists:JUnitParams:${Version.junitParam}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Version.mockWebServer}"
    const val turbine = "app.cash.turbine:turbine:${Version.turbine}"
}

object Project {
    const val remote = ":remote"
    const val libStockPrice = ":lib_stock_price"
}