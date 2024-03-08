package com.glib.core.repository.network.interceptor

import com.glib.core.repository.network.helpers.NetworkHelper
import com.glib.core.repository.network.helpers.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor (
    private val networkHelper: NetworkHelper = NetworkHelper(),
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkHelper.isOnline()) throw NoConnectivityException()

        val request = chain.request().newBuilder().build()
        return chain.proceed(request)
    }
}

