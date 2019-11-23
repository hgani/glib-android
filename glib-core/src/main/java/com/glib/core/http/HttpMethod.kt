package com.glib.core.http

enum class HttpMethod {
    POST,
    PATCH,
    DELETE,
    GET,
    PUT;

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
