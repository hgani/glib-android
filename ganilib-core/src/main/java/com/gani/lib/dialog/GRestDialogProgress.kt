package com.gani.lib.dialog

import android.content.Intent
import com.gani.lib.http.GRestCallback
import com.gani.lib.http.GRestResponse
import com.gani.lib.http.HttpAsync
import com.gani.lib.http.Rest
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.screen.GScreenContainer
import java.io.Serializable

class GRestDialogProgress : GDialogProgress() {
    override fun createNewIntentFragment(): GFragment {
        return ContentFragment().withArgs(args)
    }



    companion object {
        private val ARG_METHOD = "method"
        private val ARG_URL = "url"
        private val ARG_CALLBACK = "callback"

        fun intent(method: Rest, url: String, callback: Callback): Intent {
            return intentBuilder(GRestDialogProgress::class)
                    .withArg(ARG_METHOD, method)
                    .withArg(ARG_URL, url)
                    .withArg(ARG_CALLBACK, callback)
                    .intent
        }
    }



    interface Callback : Serializable {
        fun onRestResponse(fragment: GFragment, response: GRestResponse)
    }



    class ContentFragment : GFragment() {

        private var httpRequest: HttpAsync? = null

        override fun initContent(activity: GActivity, container: GScreenContainer) {
            val method = args.getSerializable(ARG_METHOD) as Rest
            val url = args.getString(ARG_URL)!!
            val callback = args.getSerializable(ARG_CALLBACK) as Callback

            httpRequest = method.asyncUrl(url, null, object : GRestCallback.Default(this@ContentFragment) {
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
