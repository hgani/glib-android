package com.gani.lib.http

import android.os.AsyncTask
import com.gani.lib.io.ResourceCloser
import com.gani.lib.logging.GLog
import java.io.IOException
import java.io.Serializable
import java.net.HttpURLConnection
import java.net.MalformedURLException

internal abstract class HttpDelegate(val url: String, hook: HttpHook<*>) : Serializable {

    @get:Synchronized private var isCanceled: Boolean = false
    private val hook: HttpHook<*>
    private var connection: HttpURLConnection? = null

    protected abstract val fullUrl: String

    protected abstract val method: HttpMethod

    init {
        this.hook = nonNull(hook)
        this.isCanceled = false
    }

    @Throws(MalformedURLException::class, IOException::class)
    protected abstract fun makeConnection(): HttpURLConnection

    @Throws(HttpSync.HttpCanceledException::class)
    fun launchIfNotCanceled(): GHttpResponse.Default {
        if (isCanceled) {
            throw HttpSync.HttpCanceledException()
        }
        return launch()
    }

    fun launch(): GHttpResponse.Default {
        //    GHttpResponse response = new GHttpResponse(getFullUrl());
        val response = GHttp.instance.listener.createHttpResponse(fullUrl)
        GLog.i(javaClass, "Connecting to: $fullUrl ($method)")
        try {
            synchronized(this) {
                connection = makeConnection()
            }

            // NOTE: Don't put the following code inside the previous synchronized block, because its duration depends on network,
            // causing the UI thread to lock up when calling another synchronized method such as cancel().
            // Admittedly, cancel() invoked between the two synchronized blocks in this method might only apply to the first connection and therefore
            // not cancel the redirection. This behaviour is acceptable because redirection does not happen a lot in this app
            // and only executes on certain platforms as mentioned below.
            if (wasRedirected()) {
                synchronized(this) {
                    // Honeycomb and lower does not seem to support redirection. This code should not execute at all on ICS because
                    // redirection should have been handled.
                    connection = makeRedirectConnection()
                }
            }
            response.extractFrom(connection!!)
        } catch (e: MalformedURLException) {
            response.error!!.markForNetwork(e)
        } catch (e: IOException) {
            if (!isCanceled) {
                response.error!!.markForNetwork(e)
            }
        } finally {
            ResourceCloser.close(connection)
        }
        return response
//        return hook.processResponse(response)
    }

    @Throws(IOException::class)
    private fun wasRedirected(): Boolean {
        val responseCode = connection!!.responseCode
        return responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP
    }

    @Throws(MalformedURLException::class, IOException::class)
    private fun makeRedirectConnection(): HttpURLConnection {
        val redirectUrl = connection!!.getHeaderField("Location")
        GLog.d(javaClass, "Redirected to url: " + redirectUrl)
        return GHttp.instance.listener.openConnection(redirectUrl, GImmutableParams.EMPTY, HttpMethod.GET)
    }

    // NOTE: disconnect() != cancel() therefore it does not to ensure that
    // it will prevent subsequent HTTP requests -- it's not the responsibility
    // of this class
    private fun disconnect() {
        // To prevent android.os.NetworkOnMainThreadException
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void): Void? {
                if (connection != null) {
                    connection!!.disconnect()
                }
                return null
            }
        }
    }

    @Synchronized
    fun cancel() {
        GLog.d(javaClass, "Canceling request: " + fullUrl)
        this.isCanceled = true
        disconnect()
    }

    fun launch(task: AsyncHttpTask) {
        hook.launchAsyncTask(task)
    }

    companion object {
        private const val serialVersionUID = 1L

        private fun nonNull(hook: HttpHook<*>?): HttpHook<*> {
            return hook ?: HttpHook.DUMMY
        }
    }
}
