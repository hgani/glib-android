package com.gani.lib.http

import com.gani.lib.logging.GLog
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.Serializable
import java.net.HttpURLConnection
import java.util.*

typealias HttpHeaderEntry = Map.Entry<String, List<String>>

open abstract class GHttpResponse<RR : GRestResponse>(val url: String) : Serializable {

    val rest: RR
      get() {
          if (restReponse == null) {
              restReponse = createRestResponse(string)
          }
          return restReponse as RR
      }

    var headers: Set<HttpHeaderEntry> = Collections.unmodifiableSet(HashSet<HttpHeaderEntry>())
        private set

    private var binary: ByteArray? = null
    private var string = ""
    private var restReponse: RR? = null
    var error: GHttpError.Default? = null
        internal set
    private var code: Int? = null  // Could be null, e.g. if network times out

    init {
        this.error = createError()
    }

    fun hasError(): Boolean {
        return error!!.hasError()
    }

    fun setCode(code: Int) {
        this.code = code
    }

    fun getCode(): Int {
        return code!!
    }

    fun asBinary(): ByteArray? {
        return binary
    }

    fun asString(): String {
        return string
    }

//    fun asRestResponse(): RR {
//        if (restReponse == null) {
//            restReponse = createRestResponse(string)
//        }
//        return restReponse as RR
//    }

    // To be overridden
    protected fun createRestResponse(jsonString: String): RR {
        return GRestResponse(jsonString, this) as RR
    }

//    // To be overridden
//    protected fun createError(): GHttpError<*> {
//        return GHttpError.Default(this)
//    }

    protected abstract fun createError(): GHttpError.Default

    @Throws(IOException::class)
    internal fun extractFrom(connection: HttpURLConnection) {
        val code = connection.responseCode
        setCode(code)

        this.headers = Collections.unmodifiableSet(connection.getHeaderFields().entries)

        // Redirection (e.g. 300) should have been handled prior, so we consider anything below 300 a "success".
        if (code > 300) {
            GLog.t(javaClass, "HTTP Code: " + code)
            error!!.markForCode(code)
        }

        var stream: InputStream? = null
        try {
            stream = connection.inputStream
        } catch (e: IOException) {
            // The var `stream` will be null and handled in subsequent code.
        }

        if (stream == null) {
            stream = connection.errorStream
        }

        if (stream == null) {  // Not sure if this will happen ever, but just a safe guard especially since we're dealing with API.
            this.binary = ByteArray(0)
        } else {
            this.binary = readByteArray(stream, getContentLengthForBufferring(connection))
        }
        this.string = String(binary!!)

        GLog.i(javaClass, "[$code]: $string")
    }

    override fun toString(): String {
        return asString()
    }

    companion object {
        private const val serialVersionUID = 1L

        //  void handle(GHttpCallback callback) {
        //    if (error.hasError()) {
        //      callback.onHttpFailure(error);
        //      if (!displayMessage()) {
        //        ToastUtils.showNormal(error.getMessage());
        //      }
        //    }
        //    else {
        //      callback.onHttpSuccess(this);
        //    }
        //  }

        private val BUFFER_SIZE = 102400

        private fun getContentLengthForBufferring(connection: HttpURLConnection): Int {
            try {
                return Integer.parseInt(connection.getHeaderField("Content-Length"))
            } catch (e: Exception) {
                GLog.i(GHttpResponse::class.java, "Using default buffer length, because we cant get content length from header")
                return BUFFER_SIZE
            }

        }

        @Throws(IOException::class)
        private fun readByteArray(`in`: InputStream, bufLen: Int): ByteArray {
            val buffer = ByteArray(BUFFER_SIZE)
            val bos = ByteArrayOutputStream(bufLen)
            var read = `in`.read(buffer)
            while (read > -1) {
                bos.write(buffer, 0, read)
                try {
                    read = `in`.read(buffer)
                } catch (e: IllegalStateException) {
                    // There has been ANRs on "java.lang.IllegalStateException: attempt to use Inflater after calling end"
                    // It's not good to just let the app crash, so we should treat it like a normal IOException.
                    throw IOException(e.message)
                }

            }

            bos.flush()
            val data = bos.toByteArray()
            GLog.d(GHttpResponse::class.java, "Actual HTTP result size (in bytes): " + data.size)
            return data
        }
    }


    class Default(url: String) : GHttpResponse<GRestResponse>(url) {
        override protected fun createError(): GHttpError.Default {
            return GHttpError.Default(this)
        }
    }
}
