package com.gani.lib.http

class HttpAsyncPost(nakedUrl: String, params: GImmutableParams?, hook: HttpHook<*>, method: HttpMethod, callback: GHttpCallback<GHttpResponse.Default, *>) : HttpAsync {
    override val url: String
        get() = delegate.url

    private val delegate: PostDelegate
    private val asyncTask: AsyncHttpTask

    init {
        this.delegate = PostDelegate(nakedUrl, params, hook, method)
        this.asyncTask = AsyncHttpTask(callback, delegate)
    }

    override fun execute(): HttpAsyncPost {
        delegate.launch(asyncTask)
        return this
    }

    override fun cancel() {
        delegate.cancel()
        asyncTask.safeCancel()
    }
}
