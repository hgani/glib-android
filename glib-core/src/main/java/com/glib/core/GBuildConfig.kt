package com.glib.core

abstract class GBuildConfig {
    abstract val host: String
    open val isDebugMode: Boolean
        get() = true

}
