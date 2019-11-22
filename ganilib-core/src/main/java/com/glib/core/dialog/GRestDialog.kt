package com.glib.core.dialog

import android.content.Intent
import com.glib.core.http.*
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.screen.GScreenContainer
import java.io.Serializable

class GRestDialog : GDialogProgress() {
    override fun createNewIntentFragment(): GFragment {
        return ContentFragment()
    }



    companion object {
        private val ARG_METHOD = "method"
        private val ARG_URL = "url"
        private val ARG_PARAMS = "params"
        private val ARG_CALLBACK = "callback"
        private val ARG_CLOSE_ON_LOAD = "closeOnLoad"

        fun intentForUrl(url: String, method: Rest, params: GParams.Default?, closeOnLoad: Boolean, callback: Callback): Intent {
            return intentBuilder(GRestDialog::class)
                    .withArg(ARG_METHOD, method)
                    .withArg(ARG_URL, url)
                    .withArg(ARG_PARAMS, params)
                    .withArg(ARG_CLOSE_ON_LOAD, closeOnLoad)
                    .withArg(ARG_CALLBACK, callback)
                    .intent
        }

        fun intentForPath(path: String, method: Rest, params: GParams.Default?, closeOnLoad: Boolean, callback: Callback): Intent {
            return intentForUrl("${GHttp.instance.host}${path}", method, params, closeOnLoad, callback)
        }
    }



    interface Callback : Serializable {
        fun onRestResponse(fragment: GFragment, response: GRestResponse)
    }



    class ContentFragment : GFragment() {

        private var httpRequest: HttpAsync? = null

        override fun initContent(activity: GActivity, container: GScreenContainer) {
            val method = args[ARG_METHOD].serializable as Rest
            val url = args[ARG_URL].stringValue
            val params = args[ARG_PARAMS].serializable as? GParams.Default
            val callback = args[ARG_CALLBACK].serializable as Callback
            val closeOnLoad = args[ARG_CLOSE_ON_LOAD].boolValue

            httpRequest = method.asyncUrl(url, params).execute(GRestCallback.Default(this@ContentFragment) {
                callback.onRestResponse(this@ContentFragment, it)
                if (closeOnLoad) {
                    gActivity?.finish()
                }
            })
        }

        override fun onDestroy() {
            super.onDestroy()
            httpRequest?.cancel()
        }

    }
}
