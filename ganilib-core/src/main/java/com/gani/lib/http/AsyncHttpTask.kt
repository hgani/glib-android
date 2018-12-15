package com.gani.lib.http

import android.os.AsyncTask

class AsyncHttpTask internal constructor(private val callback: GHttpCallback<GHttpResponse.Default>, private val delegate: HttpDelegate) : AsyncTask<Void, Void, GHttpResponse.Default>() {
    val url: String
        get() = delegate.url

    override fun doInBackground(vararg unused: Void): GHttpResponse.Default {
        return delegate.launch()
    }

    override fun onPostExecute(result: GHttpResponse.Default) {
        callback.onHttpResponse(result)
    }

    // NOTE: calling this to ensure that no future call to executeIfNotCanceled() will take effect
    @Synchronized internal fun safeCancel() {  // Sync with executeIfNotCanceled()
        cancel(true)
    }

    @Synchronized
    fun firstPhaseExecute() {
        (callback as? GRestCallback<*, *>)?.onBeforeHttp()
    }

    @Synchronized
    fun secondPhaseExecute() {
        if (!isCancelled) {
            executeOnExecutor(THREAD_POOL_EXECUTOR)
        }
    }

    @Synchronized
    fun executeIfNotCanceled() {
        firstPhaseExecute()
        secondPhaseExecute()
    }
//<<<<<<< HEAD
//
//    companion object {
//        // Taken from AsyncTask
//        //  private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
//        //  private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
//        private val KEEP_ALIVE_SECONDS = 30
//        private val QUEUE = LinkedBlockingQueue<Runnable>(128)
//
//        private val MAXIMUM_POOL_SIZE = 20
//        // New threads only get added when the queue is full, so we just put core size to be the maximum right from the beginning.
//        // See https://stackoverflow.com/questions/19528304/how-to-get-the-threadpoolexecutor-to-increase-threads-to-max-before-queueing
//        private val CORE_POOL_SIZE = MAXIMUM_POOL_SIZE
//
//        // Allow async tasks to execute in parallel
//        val THREAD_POOL_EXECUTOR: Executor
//
//        init {
//            val threadPoolExecutor = ThreadPoolExecutor(
//                    CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS.toLong(), TimeUnit.SECONDS, QUEUE)
//            threadPoolExecutor.allowCoreThreadTimeOut(true)
//            THREAD_POOL_EXECUTOR = threadPoolExecutor
//        }
//    }
//=======
//>>>>>>> room
}