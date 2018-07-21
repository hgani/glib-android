package com.gani.lib.http


class HttpAsyncGet(nakedUrl: String, params: GImmutableParams?, hook: HttpHook<*>, callback: GHttpCallback<GHttpResponse.Default, *>) : HttpAsync {
    override val url: String
        get() = delegate.url

    private val delegate: GetDelegate
    private val asyncTask: AsyncHttpTask

    init {
        this.delegate = GetDelegate(nakedUrl, params, hook)
        this.asyncTask = AsyncHttpTask(callback, delegate)
    }

    override fun execute(): HttpAsyncGet {
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
