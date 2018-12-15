package com.gani.lib.http

enum class Http {
    POST {
        override fun async(url: String, params: GParams.Default): HttpAsync {
            return HttpAsyncPost(url, params.toImmutable(), HttpHook.DUMMY, HttpMethod.POST)
        }
    },
    PATCH {
        override fun async(url: String, params: GParams.Default): HttpAsync {
            return HttpAsyncPost(url, params.toImmutable(), HttpHook.DUMMY, HttpMethod.PATCH)
        }
    },
    DELETE {
        override fun async(url: String, params: GParams.Default): HttpAsync {
            return HttpAsyncPost(url, params.toImmutable(), HttpHook.DUMMY, HttpMethod.DELETE)
        }
    },
    GET {
        override fun async(url: String, params: GParams.Default): HttpAsync {
            return HttpAsyncGet(url, params.toImmutable(), HttpHook.DUMMY)
        }
    },
    PUT {
        override fun async(url: String, params: GParams.Default): HttpAsync {
            return HttpAsyncPost(url, params.toImmutable(), HttpHook.DUMMY, HttpMethod.PUT)
        }
    };

    abstract fun async(url: String, params: GParams.Default): HttpAsync
}

