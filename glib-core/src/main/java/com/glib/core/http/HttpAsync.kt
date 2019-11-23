package com.glib.core.http

interface HttpAsync {
    val url: String
//    fun execute(): HttpAsync
    fun cancel()
    fun execute(callback: GHttpCallback<GHttpResponse.Default>): HttpAsync
}
