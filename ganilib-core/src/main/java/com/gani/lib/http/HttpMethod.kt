package com.gani.lib.http

enum class HttpMethod {
    POST,
    PATCH,
    DELETE,
    GET,
    PUT;

//    POST {
//        override fun async(url: String, params: GParams.Default, callback: GHttpCallback<*, *>): HttpAsync {
//            return HttpAsyncPost(url, params.toImmutable(), HttpHook.DUMMY, POST, callback)
//        }
//    },
//    PATCH {
//        override fun async(url: String, params: GParams.Default, callback: GHttpCallback<*, *>): HttpAsync {
//            return HttpAsyncPost(url, params.toImmutable(), HttpHook.DUMMY, PATCH, callback)
//        }
//    },
//    DELETE {
//        override fun async(url: String, params: GParams.Default, callback: GHttpCallback<*, *>): HttpAsync {
//            return HttpAsyncPost(url, params.toImmutable(), HttpHook.DUMMY, DELETE, callback)
//        }
//    },
//    GET {
//        override fun async(url: String, params: GParams.Default, callback: GHttpCallback<*, *>): HttpAsync {
//            return HttpAsyncGet(url, params.toImmutable(), HttpHook.DUMMY, callback)
//        }
//    },
//    PUT {
//        override fun async(url: String, params: GParams.Default, callback: GHttpCallback<*, *>): HttpAsync {
//            return HttpAsyncPost(url, params.toImmutable(), HttpHook.DUMMY, PUT, callback)
//        }
//    };
//
//    abstract fun async(url: String, params: GParams.Default, callback: GHttpCallback<*, *>): HttpAsync

    companion object {

        fun from(method: String): HttpMethod {
            when (method) {
                "patch" -> return PATCH
                "put" -> return PUT
                "delete" -> return DELETE
                else -> return POST
            }
        }

        fun from(params: GParams<*, *>): HttpMethod {
            val method = params.get("_method") as String ?: return POST
            return from(method)
        }
    }


}
