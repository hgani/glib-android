package com.gani.lib.http

enum class Http {
    POST {
        override fun async(url: String, params: GParams.Default, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncPost(url, params.toImmutable(), HttpHook.DUMMY, HttpMethod.POST, callback)
        }
    },
    PATCH {
        override fun async(url: String, params: GParams.Default, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncPost(url, params.toImmutable(), HttpHook.DUMMY, HttpMethod.PATCH, callback)
        }
    },
    DELETE {
        override fun async(url: String, params: GParams.Default, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncPost(url, params.toImmutable(), HttpHook.DUMMY, HttpMethod.DELETE, callback)
        }
    },
    GET {
        override fun async(url: String, params: GParams.Default, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncGet(url, params.toImmutable(), HttpHook.DUMMY, callback)
        }
    },
    PUT {
        override fun async(url: String, params: GParams.Default, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncPost(url, params.toImmutable(), HttpHook.DUMMY, HttpMethod.PUT, callback)
        }
    };

    abstract fun async(url: String, params: GParams.Default, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync
}

