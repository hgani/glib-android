package com.gani.lib.http

class HttpAsyncPost(nakedUrl: String, params: GImmutableParams?, method: HttpMethod) : HttpAsync {
    override val url: String
        get() = delegate.url

    private val delegate: PostDelegate
    private lateinit var asyncTask: AsyncHttpTask

    init {
        this.delegate = PostDelegate(nakedUrl, params, method)
    }

    override fun execute(callback: GHttpCallback<GHttpResponse.Default>): HttpAsyncPost {
        this.asyncTask = AsyncHttpTask(callback, delegate)
        delegate.launch(asyncTask)
        return this
    }

    override fun cancel() {
        delegate.cancel()
        asyncTask.safeCancel()
    }
}
