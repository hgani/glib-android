package com.gani.lib.http

enum class Rest {
    POST {
        override fun async(path: String, params: GParams.Default?, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncPost(urlFrom(path), params?.toImmutable(), HttpHook.DUMMY, HttpMethod.POST, callback)
        }
    },
    PATCH {
        override fun async(path: String, params: GParams.Default?, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncPost(urlFrom(path), params?.toImmutable(), HttpHook.DUMMY, HttpMethod.PATCH, callback)
        }
    },
    DELETE {
        override fun async(path: String, params: GParams.Default?, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncPost(urlFrom(path), params?.toImmutable(), HttpHook.DUMMY, HttpMethod.DELETE, callback)
        }
    },
    GET {
        override fun async(path: String, params: GParams.Default?, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncGet(urlFrom(path), params?.toImmutable(), HttpHook.DUMMY, callback)
        }
    },
    PUT {
        override fun async(path: String, params: GParams.Default?, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncPost(urlFrom(path), params?.toImmutable(), HttpHook.DUMMY, HttpMethod.PUT, callback)
        }
    };

    protected fun urlFrom(path: String): String {
        return "${GHttp.instance.host}${path}"
    }

    abstract fun async(url: String, params: GParams.Default? = null, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync
}