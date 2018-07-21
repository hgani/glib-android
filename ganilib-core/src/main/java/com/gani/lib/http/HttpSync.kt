package com.gani.lib.http


interface HttpSync<HR : GHttpResponse<*>> {
    val url: String
    @Throws(HttpSync.HttpSyncException::class, HttpSync.HttpCanceledException::class)
    fun execute(): HR

    fun cancel()


    class HttpSyncException internal constructor(val error: GHttpError.Default) : Exception() {
        override val message: String?
            get() = error.message

//        override fun getMessage(): String? {
//            return error.message
//        }

        companion object {
            private val serialVersionUID = 1L
        }
    }

    // Just a tagging exception.
    class HttpCanceledException : Exception() {
        companion object {
            private val serialVersionUID = 1L
        }
    }
}
