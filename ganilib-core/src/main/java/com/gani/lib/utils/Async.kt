package com.gani.lib.utils

import android.os.AsyncTask
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object Async {
    fun run(command: () -> Unit) {
        //    uiHandler.post(command);
        DefaultAsyncTask(command).executeOnExecutor(THREAD_POOL_EXECUTOR)
    }


//    companion object {
        // Taken from AsyncTask
        //  private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
        //  private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
        private val KEEP_ALIVE_SECONDS = 30
        private val QUEUE = LinkedBlockingQueue<Runnable>(128)

        private val MAXIMUM_POOL_SIZE = 20
        // New threads only get added when the queue is full, so we just put core size to be the maximum right from the beginning.
        // See https://stackoverflow.com/questions/19528304/how-to-get-the-threadpoolexecutor-to-increase-threads-to-max-before-queueing
        private val CORE_POOL_SIZE = MAXIMUM_POOL_SIZE

        val THREAD_POOL_EXECUTOR: Executor

        init {
            val threadPoolExecutor = ThreadPoolExecutor(
                    CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS.toLong(), TimeUnit.SECONDS, QUEUE)
            threadPoolExecutor.allowCoreThreadTimeOut(true)
            THREAD_POOL_EXECUTOR = threadPoolExecutor
        }
//    }

    class DefaultAsyncTask : AsyncTask<Void, Void, Void> {
        private val command: () -> Unit

//        {
//
//        }
        constructor(command: () -> Unit) { this.command = command }


        override fun doInBackground(vararg params: Void): Void? {
            command()

//            try {
//                onRestResponse(r)
//            } catch (e: JSONException) {
//                return e
//            }

            return null
        }

//        override fun onPostExecute(e: Exception) {
////            if (e is JSONException) {
////                //          GHttp.instance().alertHelper().alertJsonError(context, r, (JSONException) e);
////                onJsonError(r, e)
////            }
////
////            doFinally()
//        }
        //      }.execute();

        fun execute() {

        }

    }
//    .executeOnExecutor(AsyncHttpTask.THREAD_POOL_EXECUTOR)
}
