import com.android.build.api.variant.BuildConfigField

plugins {
    androidLib
}

android {
    namespace = "com.ezike.tobenna.remote"
    buildFeatures {
        buildConfig = true
    }
}

androidComponents {
    onVariants {
        it.buildConfigFields.put(
            /* key = */ "SOCKET_ADDRESS",
            /* value = */ BuildConfigField(
                type = "String",
                value = "\"ws://46.101.236.188:8080/\"",
                comment = null,
            )
        )
    }
}

dependencies {
    implementation(
        Library.scarlet,
        Library.scarletLifecycle,
        Library.scarletMoshi,
        Library.scarletOkhttp,
        Library.okhttpLogger
    )
    testImplementation(Library.mockWebServer)
    kapt(Library.moshiCodeGen)
}
