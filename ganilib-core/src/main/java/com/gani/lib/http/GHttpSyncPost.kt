package com.gani.lib.http

class GHttpSyncPost(nakedUrl: String, params: GImmutableParams, method: HttpMethod) : HttpSync<GHttpResponse.Default> {
    private val delegate: PostDelegate

    override val url: String
        get() = delegate.url

    init {
        this.delegate = PostDelegate(nakedUrl, params, method)
    }

    @Throws(HttpSync.HttpSyncException::class, HttpSync.HttpCanceledException::class)
    override fun execute(): GHttpResponse.Default {
        val response = delegate.launchIfNotCanceled()
        if (response.error!!.hasError()) {
            throw HttpSync.HttpSyncException(response.error!!)
        }
        return response
    }

    override fun cancel() {
        delegate.cancel()
    }
}
