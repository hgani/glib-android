package com.gani.lib.http

enum class Rest {
    POST {
        override fun asyncUrl(url: String, params: GParams.Default?): HttpAsync {
            return HttpAsyncPost(url, params?.toImmutable(), HttpHook.DUMMY, HttpMethod.POST)
        }
    },
    PATCH {
        override fun asyncUrl(url: String, params: GParams.Default?): HttpAsync {
            return HttpAsyncPost(url, params?.toImmutable(), HttpHook.DUMMY, HttpMethod.PATCH)
        }
    },
    DELETE {
        override fun asyncUrl(url: String, params: GParams.Default?): HttpAsync {
            return HttpAsyncPost(url, params?.toImmutable(), HttpHook.DUMMY, HttpMethod.DELETE)
        }
    },
    GET {
        override fun asyncUrl(url: String, params: GParams.Default?): HttpAsync {
            return HttpAsyncGet(url, params?.toImmutable(), HttpHook.DUMMY)
        }
    },
    PUT {
        override fun asyncUrl(url: String, params: GParams.Default?): HttpAsync {
            return HttpAsyncPost(url, params?.toImmutable(), HttpHook.DUMMY, HttpMethod.PUT)
        }
    };

    protected fun urlFrom(path: String): String {
        return "${GHttp.instance.host}${path}"
    }

    fun async(path: String, params: GParams.Default? = null, prependHost: Boolean = true): HttpAsync {
        val url = if (prependHost) urlFrom(path) else path
        return asyncUrl(url, params)
    }

    abstract fun asyncUrl(url: String, params: GParams.Default? = null): HttpAsync



    companion object {
        fun from(method: String): Rest {
            when (method) {
                "patch" -> return Rest.PATCH
                "put" -> return Rest.PUT
                "delete" -> return Rest.DELETE
                else -> return Rest.POST
            }
        }

//        fun from(params: GParams<*, *>): HttpMethod {
//            val method = params.get("_method") as String ?: return HttpMethod.POST
//            return from(method)
//        }
    }
}