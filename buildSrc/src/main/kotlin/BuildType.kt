interface BuildType {

    val name: String
    val isMinifyEnabled: Boolean
    val isTestCoverageEnabled: Boolean
    val applicationIdSuffix: String
    val versionNameSuffix: String

    companion object {
        val Debug: BuildType = BuildTypeDebug
        val Release: BuildType = BuildTypeRelease
    }
}

private object BuildTypeDebug : BuildType {
    override val name: String get() = "debug"
    override val isMinifyEnabled: Boolean get() = false
    override val isTestCoverageEnabled: Boolean get() = true
    override val applicationIdSuffix: String get() = ".$name"
    override val versionNameSuffix: String get() = "-${name.toUpperCase()}"
}

private object BuildTypeRelease : BuildType {
    override val name: String get() = "release"
    override val isMinifyEnabled: Boolean get() = true
    override val isTestCoverageEnabled: Boolean get() = true
    override val applicationIdSuffix: String get() = ".$name"
    override val versionNameSuffix: String get() = "-${name.toUpperCase()}"
}
