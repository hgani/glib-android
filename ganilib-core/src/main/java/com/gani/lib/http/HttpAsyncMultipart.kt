package com.gani.lib.http


class HttpAsyncMultipart(nakedUrl: String, params: GImmutableParams, uploads: Map<String, Uploadable>, hook: HttpHook<*>) : HttpAsync {
    override val url: String
        get() = delegate.url

    private val delegate: MultipartDelegate
    private lateinit var asyncTask: AsyncHttpTask

    init {
        this.delegate = MultipartDelegate(nakedUrl, params, uploads, hook)
    }

//    override fun execute(): HttpAsyncMultipart {
//        delegate.launch(asyncTask)
//        return this
//    }

    override fun execute(callback: GHttpCallback<GHttpResponse.Default>): HttpAsyncMultipart {
        this.asyncTask = AsyncHttpTask(callback, delegate)
        delegate.launch(asyncTask)
        return this
    }

    override fun cancel() {
        delegate.cancel()
        asyncTask.safeCancel()
    }


    class Uploadable(val fileName: String, val mimeType: String, val data: ByteArray)
}
