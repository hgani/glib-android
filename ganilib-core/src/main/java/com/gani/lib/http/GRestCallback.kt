package com.gani.lib.http

import android.content.Context
import com.gani.lib.logging.GLog
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.ProgressIndicator
import com.gani.lib.notification.Toast


abstract class GRestCallback<HR : GHttpResponse<RR>, RR : GRestResponse, HE : GHttpError<*>>(// To be used by child.
        protected val context: Context, private val indicator: ProgressIndicator) : GHttpCallback<HR, HE> {
//    private var async: Boolean = false

//    constructor(fragment: GFragment) : this(fragment.context, fragment.indicator) {}
    constructor(fragment: GFragment) : this(fragment.context!!, fragment.indicator) {}

//    constructor(dialog: GDialogProgress) : this(dialog, dialog) {}

//    fun async(value: Boolean): GRestCallback<*, *, *> {
//        this.async = value
//        return this
//    }

    final override fun onHttpResponse(response: HR) {
        val r = response.rest

//        if (this.async) {
//            object : AsyncTask<Void, Void, Exception>() {
//                override fun doInBackground(vararg params: Void): Exception? {
//                    try {
//                        onRestResponse(r)
//                    } catch (e: JSONException) {
//                        return e
//                    }
//
//                    return null
//                }
//
//                override fun onPostExecute(e: Exception) {
//                    if (e is JSONException) {
//                        //          GHttp.instance().alertHelper().alertJsonError(context, r, (JSONException) e);
//                        onJsonError(r, e)
//                    }
//
//                    doFinally()
//                }
//                //      }.execute();
//
//            }.executeOnExecutor(AsyncHttpTask.THREAD_POOL_EXECUTOR)
//        } else {
//            try {
//                onRestResponse(r)
//            } catch (e: JSONException) {
//                onJsonError(r, e)
//            } finally {
//                doFinally()
//            }
//        }

        try {
            onRestResponse(r)
        } finally {
            doFinally()
        }
    }
//
//    protected fun onJsonError(response: RR, e: JSONException) {
//        GLog.e(javaClass, "Failed parsing JSON result", e)
//    }

    fun onBeforeHttp() {
        GLog.t(javaClass, "SHOW PROGRESS1")
        indicator.showProgress()
    }

    open protected fun onRestResponse(r: RR) {
        displayMessage(r)
    }

    protected fun doFinally() {
        indicator.hideProgress()
    }

    companion object {
        fun displayMessage(r: GRestResponse): Boolean {
            val restData = r.result
            val message = restData.json("message").string
            if (message != null) {
                Toast.center(message)
                return true
            }
            return false
        }
    }

    open class Default : GRestCallback<GHttpResponse.Default, GRestResponse, GHttpError.Default> {
//        constructor(context: Context, indicator: ProgressIndicator) : super(context, indicator) {}

        constructor(fragment: GFragment) : super(fragment) {}
    }

}
