package com.gani.lib.http

import com.gani.lib.logging.GLog
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection

internal class MultipartDelegate(nakedUrl: String, params: GImmutableParams, uploads: Map<String, HttpAsyncMultipart.Uploadable>, hook: HttpHook<*>) : HttpDelegate(nakedUrl, hook) {
    private val params: GImmutableParams
    private val uploads: Map<String, HttpAsyncMultipart.Uploadable>
    override protected val method = HttpMethod.POST

//    override val method: String
//        get() = HttpMethod.POST.name

    override val fullUrl: String
        get() = url

    init {
        this.params = GImmutableParams.fromNullable(params)
        this.uploads = nonNullImmutable(uploads)
    }

    private fun nonNullImmutable(uploads: Map<String, HttpAsyncMultipart.Uploadable>?): Map<String, HttpAsyncMultipart.Uploadable> {
        return uploads ?: emptyMap()
    }

    @Throws(IOException::class)
    override fun makeConnection(): HttpURLConnection {
        val connection = GHttp.instance.listener.openConnection(fullUrl, params, HttpMethod.POST)
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + MESSAGE_BOUNDARY)
        connection.setDoOutput(true)

        val data = createMultipartData(params, uploads)
        GLog.d(javaClass, "Multipart data: " + data.size)
        // NOTE: Not sure whether using setFixedLengthStreamingMode() will result in error 400 if the server tells the client to
        // redirect (See relevant comment on PostDelegate).
        connection.setFixedLengthStreamingMode(data.size)
        connection.getOutputStream().write(data)
        return connection
    }

    companion object {
        private val MESSAGE_BOUNDARY = "----------V2ymHFg03ehbqgZCaKO6jy"
        private val serialVersionUID = 1L

        @Throws(IOException::class)
        private fun createMultipartData(params: GImmutableParams, uploads: Map<String, HttpAsyncMultipart.Uploadable>): ByteArray {
            val endBoundary = "\r\n--$MESSAGE_BOUNDARY--\r\n"

            val baos = ByteArrayOutputStream()
            val osw = OutputStreamWriter(baos)

            osw.write("--" + MESSAGE_BOUNDARY + "\r\n")
            // Not sure why casting is required.
            for ((key, value) in params.entrySet()) {
                // NOTE: For now we just assume all values are String. We don't support String[].
                osw.write("Content-Disposition: form-data; name=\"" + key +
                        "\"\r\n\r\n" + value + "\r\n--" + MESSAGE_BOUNDARY + "\r\n")
            }

            for ((fileField, upload) in uploads) {

                osw.write("Content-Disposition: form-data; name=\"" + fileField +
                        "\"; filename=\"" + upload.fileName + "\"\r\nContent-Type: " + upload.mimeType + "\r\n\r\n")
                osw.flush()
                baos.write(upload.data)
                osw.write(endBoundary)
            }

            osw.flush()
            return baos.toByteArray()
        }
    }
}
