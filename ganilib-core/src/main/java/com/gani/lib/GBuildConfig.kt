package com.gani.lib

abstract class GBuildConfig {
    abstract val host: String
    open val isDebugMode: Boolean
        get() = true

}
