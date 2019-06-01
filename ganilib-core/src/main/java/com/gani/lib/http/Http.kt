package com.gani.lib.http

enum class Http {
    POST {
        override fun async(url: String, params: GParams.Default): HttpAsync {
            return HttpAsyncPost(url, params.toImmutable(), HttpMethod.POST)
        }
    },
    PATCH {
        override fun async(url: String, params: GParams.Default): HttpAsync {
            return HttpAsyncPost(url, params.toImmutable(), HttpMethod.PATCH)
        }
    },
    DELETE {
        override fun async(url: String, params: GParams.Default): HttpAsync {
            return HttpAsyncPost(url, params.toImmutable(), HttpMethod.DELETE)
        }
    },
    GET {
        override fun async(url: String, params: GParams.Default): HttpAsync {
            return HttpAsyncGet(url, params.toImmutable())
        }
    },
    PUT {
        override fun async(url: String, params: GParams.Default): HttpAsync {
            return HttpAsyncPost(url, params.toImmutable(), HttpMethod.PUT)
        }
    };

    abstract fun async(url: String, params: GParams.Default): HttpAsync
}

