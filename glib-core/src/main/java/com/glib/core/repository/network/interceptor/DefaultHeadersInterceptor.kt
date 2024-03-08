package com.glib.core.repository.network.interceptor

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response


class DefaultHeadersInterceptor : Interceptor {
    override fun intercept(chain: Chain): Response {

        val request = chain.request().newBuilder()

//            .header(HEADER_NAME_BUILD, BuildConfig.GITID)
//            .header(HEADER_NAME_VERSION, BuildConfig.VERSION_NAME)
//            .header(HEADER_NAME_DEVICE_OS, HEADER_VALUE_DEVICE_OS)
//            .header(HEADER_NAME_DEVICE_MANUFACTURER, android.os.Build.MANUFACTURER)
//            .header(HEADER_NAME_DEVICE_MODEL, android.os.Build.MODEL)
//            .header(HEADER_NAME_DEVICE_WIDTH, Integer.valueOf(Length.windowWidthPx()).toString())
//            .header(HEADER_NAME_DEVICE_HEIGHT, Integer.valueOf(Length.windowHeightPx()).toString())
//            .header(HEADER_NAME_LANGUAGE, normalizeLanguageCode(Locale.getDefault().language))

//            .header("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 12; sdk_gphone64_arm64 Build/SE1A.220630.001.A1)")
//            .header("Content-Type", "application/x-www-form-urlencoded")

//        DbMap.get(MyFirebaseMessagingService.DEVICE_UUID)?.let {
//            request.header(HEADER_NAME_DEVICE_REMOTE_DEVICE_UUID, it)
//        }

        return chain.proceed(request.build())
    }


    private fun normalizeLanguageCode(code: String?): String {
        return if (code == null) {
            ""
        } else when (code) {
            "in" -> "id"
            else -> code
        }
    }
}