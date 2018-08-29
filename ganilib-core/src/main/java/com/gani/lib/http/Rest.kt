package com.gani.lib.http

enum class Rest {
    POST {
        override fun asyncUrl(url: String, params: GParams.Default?, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncPost(url, params?.toImmutable(), HttpHook.DUMMY, HttpMethod.POST, callback)
        }
    },
    PATCH {
        override fun asyncUrl(url: String, params: GParams.Default?, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncPost(url, params?.toImmutable(), HttpHook.DUMMY, HttpMethod.PATCH, callback)
        }
    },
    DELETE {
        override fun asyncUrl(url: String, params: GParams.Default?, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncPost(url, params?.toImmutable(), HttpHook.DUMMY, HttpMethod.DELETE, callback)
        }
    },
    GET {
        override fun asyncUrl(url: String, params: GParams.Default?, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncGet(url, params?.toImmutable(), HttpHook.DUMMY, callback)
        }
    },
    PUT {
        override fun asyncUrl(url: String, params: GParams.Default?, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
            return HttpAsyncPost(url, params?.toImmutable(), HttpHook.DUMMY, HttpMethod.PUT, callback)
        }
    };

    protected fun urlFrom(path: String): String {
        return "${GHttp.instance.host}${path}"
    }

    fun async(path: String, params: GParams.Default? = null, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync {
        return asyncUrl(urlFrom(path), params, callback)
    }

    abstract fun asyncUrl(url: String, params: GParams.Default? = null, callback: GHttpCallback<GHttpResponse.Default, *>): HttpAsync



    companion object {
        fun from(method: String): HttpMethod {
            when (method) {
                "patch" -> return HttpMethod.PATCH
                "put" -> return HttpMethod.PUT
                "delete" -> return HttpMethod.DELETE
                else -> return HttpMethod.POST
            }
        }

//        fun from(params: GParams<*, *>): HttpMethod {
//            val method = params.get("_method") as String ?: return HttpMethod.POST
//            return from(method)
//        }
    }
}