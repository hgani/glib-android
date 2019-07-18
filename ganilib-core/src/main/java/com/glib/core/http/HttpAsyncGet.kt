package com.glib.core.http


class HttpAsyncGet(nakedUrl: String, params: GImmutableParams?) : HttpAsync {
    override val url: String
        get() = delegate.url

    private val delegate: GetDelegate
    private lateinit var asyncTask: AsyncHttpTask

    init {
        this.delegate = GetDelegate(nakedUrl, params)
    }

    override fun execute(callback: GHttpCallback<GHttpResponse.Default>): HttpAsyncGet {
        this.asyncTask = AsyncHttpTask(callback, delegate)
        delegate.launch(asyncTask)
        return this
    }

    override fun cancel() {
        delegate.cancel()
        asyncTask.safeCancel()
    }

    fun getParam(key: String): Any? {
        return delegate.getParam(key)
    }
}
