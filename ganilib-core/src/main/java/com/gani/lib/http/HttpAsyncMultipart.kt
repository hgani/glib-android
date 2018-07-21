package com.gani.lib.http


class HttpAsyncMultipart(nakedUrl: String, params: GImmutableParams, uploads: Map<String, Uploadable>, hook: HttpHook<*>, callback: GHttpCallback<GHttpResponse.Default, *>) : HttpAsync {
    override val url: String
        get() = delegate.url

    private val delegate: MultipartDelegate
    private val asyncTask: AsyncHttpTask

    init {
        this.delegate = MultipartDelegate(nakedUrl, params, uploads, hook)
        this.asyncTask = AsyncHttpTask(callback, delegate)
    }

    override fun execute(): HttpAsync {
        delegate.launch(asyncTask)
        return this
    }

    override fun cancel() {
        delegate.cancel()
        asyncTask.safeCancel()
    }


    class Uploadable(val fileName: String, val mimeType: String, val data: ByteArray)
}
