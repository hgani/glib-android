package com.gani.lib.http


class HttpSyncGet(nakedUrl: String, params: GImmutableParams) : HttpSync<GHttpResponse.Default> {
    override val url: String
        get() = delegate.url

    private val delegate: GetDelegate

    init {
        this.delegate = GetDelegate(nakedUrl, params)
    }

    @Throws(HttpSync.HttpSyncException::class, HttpSync.HttpCanceledException::class)
    override fun execute(): GHttpResponse.Default {
        val response = delegate.launchIfNotCanceled()
        if (response.error!!.hasError()) {
            throw HttpSync.HttpSyncException(response.error!!)
        }
        return response
    }

    fun execute(callback: GHttpCallback<GHttpResponse.Default>) {
        try {
            callback.onHttpResponse(execute())
            //      callback.onHttpSuccess(execute());
        } catch (e: HttpSync.HttpSyncException) {
            callback.onHttpResponse(e.error.response)
            //      callback.onHttpFailure(e.getError());
        } catch (e: HttpSync.HttpCanceledException) {
            // Be silent for now.
        }

    }

    override fun cancel() {
        delegate.cancel()
    }
}
