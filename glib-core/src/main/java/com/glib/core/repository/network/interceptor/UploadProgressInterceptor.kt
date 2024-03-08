package com.glib.core.repository.network.interceptor

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import okio.BufferedSink
import okio.ForwardingSink
import okio.Sink
import okio.buffer

// Progress can be tracked for writing bytes to request body and not actual server uploads
class UploadProgressInterceptor(private val progressListener: UploadProgressListener) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .method(originalRequest.method, originalRequest.body?.let { ProgressRequestBody(delegate = it, progressListener) })
            .build()
        return chain.proceed(request)
    }
}


class ProgressRequestBody(
    private val delegate: RequestBody,
    private val progressListener: UploadProgressListener
) : RequestBody() {
    override fun contentLength(): Long = delegate.contentLength()

    override fun contentType(): MediaType? = delegate.contentType()

    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink)
        val bufferedSink = countingSink.buffer()
        delegate.writeTo(bufferedSink)
        bufferedSink.flush()
    }

    inner class CountingSink(delegate: Sink) : ForwardingSink(delegate) {
        private var bytesWritten = 0L

        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            bytesWritten += byteCount
            progressListener.onProgressUpdate(bytesWritten, contentLength())
        }
    }
}

interface UploadProgressListener {
    fun onProgressUpdate(bytesUploaded: Long, totalBytes: Long)
}