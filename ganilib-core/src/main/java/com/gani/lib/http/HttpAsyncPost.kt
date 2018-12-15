package com.gani.lib.http

class HttpAsyncPost(nakedUrl: String, params: GImmutableParams?, hook: HttpHook<*>, method: HttpMethod) : HttpAsync {
    override val url: String
        get() = delegate.url

    private val delegate: PostDelegate
    private lateinit var asyncTask: AsyncHttpTask

    init {
        this.delegate = PostDelegate(nakedUrl, params, hook, method)
    }

//    override fun execute(): HttpAsyncPost {
//        delegate.launch(asyncTask)
//        return this
//    }

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
