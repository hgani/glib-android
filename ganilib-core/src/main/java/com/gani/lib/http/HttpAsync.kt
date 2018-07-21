package com.gani.lib.http

interface HttpAsync {
    val url: String
    fun execute(): HttpAsync
    fun cancel()
}
