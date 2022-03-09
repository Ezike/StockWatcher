plugins {
    androidLib
}

android.defaultConfig.buildConfigField(
    type = "String",
    name = "SOCKET_ADDRESS",
    value = "\"ws://46.101.236.188:8080/\""
)

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
