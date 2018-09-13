package com.gani.lib.dialog

import android.content.Intent
import com.gani.lib.http.*
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.screen.GScreenContainer
import java.io.Serializable

class GRestDialogProgress : GDialogProgress() {
    override fun createNewIntentFragment(): GFragment {
        return ContentFragment()
    }



    companion object {
        private val ARG_METHOD = "method"
        private val ARG_URL = "url"
        private val ARG_PARAMS = "params"
        private val ARG_CALLBACK = "callback"

        fun intentForUrl(url: String, method: Rest, params: GParams.Default?, callback: Callback): Intent {
            return intentBuilder(GRestDialogProgress::class)
                    .withArg(ARG_METHOD, method)
                    .withArg(ARG_URL, url)
                    .withArg(ARG_PARAMS, params)
                    .withArg(ARG_CALLBACK, callback)
                    .intent
        }

        fun intentForPath(path: String, method: Rest, params: GParams.Default?, callback: Callback): Intent {
            return intentForUrl("${GHttp.instance.host}${path}", method, params, callback)
        }
    }



    interface Callback : Serializable {
        fun onRestResponse(fragment: GFragment, response: GRestResponse)
    }



    class ContentFragment : GFragment() {

        private var httpRequest: HttpAsync? = null

        override fun initContent(activity: GActivity, container: GScreenContainer) {
            val method = args[ARG_METHOD] as Rest
            val url = args[ARG_URL] as String
            val params = args[ARG_PARAMS] as? GParams.Default
            val callback = args[ARG_CALLBACK] as Callback

            httpRequest = method.asyncUrl(url, params, object : GRestCallback.Default(this@ContentFragment) {
                override fun onRestResponse(response: GRestResponse) {
                    super.onRestResponse(response)

                    callback.onRestResponse(this@ContentFragment, response)
                    gActivity?.finish()
                }
            }).execute()
        }

        override fun onDestroy() {
            super.onDestroy()
            httpRequest?.cancel()
        }

    }
}
