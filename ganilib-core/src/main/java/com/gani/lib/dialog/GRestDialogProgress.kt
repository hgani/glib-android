package com.gani.lib.dialog

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import com.gani.lib.http.GRestCallback
import com.gani.lib.http.GRestResponse
import com.gani.lib.http.HttpAsync
import com.gani.lib.http.Rest
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.screen.GScreenContainer
import com.gani.lib.ui.view.GProgressBar
import java.io.Serializable

class GRestDialogProgress : GDialogProgress() {
    private var httpRequest: HttpAsync? = null

    override fun onCreate(savedInstanceState: Bundle?) {

//        val view = MyScreenView(this)
//        super.onCreateForScreen(savedInstanceState, view)
//        setFragmentWithToolbar(createNewIntentFragment(), topNav(), savedInstanceState)

//        super.onCreateForDialog(savedInstanceState)

//        GLog.t(javaClass, "onCreate1")
        super.onCreate(savedInstanceState)
//
//        GLog.t(javaClass, "onCreate2")

//        val method = args.getSerializable(ARG_METHOD) as Rest
//        val url = args.getString(ARG_URL)!!
////        val params = args.getSerializable(ARG_PARAMS) as GParams.Default
//        val callback = args.getSerializable(ARG_CALLBACK) as Callback
//
//        httpRequest = method.asyncUrl(url, null, object : GRestCallback.Default(this@GRestDialogProgress) {
//            //      @Override
//            //      protected void onJsonSuccess(GRestResponse r) throws JSONException {
//            //        callback.onJsonSuccess(GRestDialogProgress.this, r);
//            //      }
//
////            @Throws(JSONException::class)
////            protected override fun onRestResponse(r: GRestResponse) {
////                callback.onRestResponse(this@GRestDialogProgress, r)
////            }
//
//            override fun onRestResponse(response: GRestResponse) {
//                callback.onRestResponse(this@GRestDialogProgress, response)
//            }
//        }).execute()
    }

    override fun createNewIntentFragment(): GFragment {
        return ContentFragment()
    }

    override fun onCancel() {
        httpRequest?.cancel()
    }


    abstract class Callback : Serializable {
        // To be overridden
//        @Throws(JSONException::class)
        open fun onRestResponse(dialogProgress: GRestDialogProgress, response: GRestResponse) {
            GRestCallback.displayMessage(response)
        }
    }

    companion object {
        private val ARG_METHOD = "method"
        private val ARG_URL = "url"
//        private val ARG_PARAMS = "params"
        private val ARG_CALLBACK = "callback"

        fun intent(method: Rest, url: String, callback: Callback): Intent {
            return intentBuilder(GRestDialogProgress::class)
                    .withArg(ARG_METHOD, method)
                    .withArg(ARG_URL, url)
//                    .withArg(ARG_PARAMS, params)
                    .withArg(ARG_CALLBACK, callback)
                    .intent
        }
    }



    class ContentFragment : GFragment() {
        override fun initContent(activity: GActivity, container: GScreenContainer) {
//            container.addView(GTextView(activity).text("TEST1"))
            container.content
                    .bgColor(Color.RED)
                    .gravity(Gravity.CENTER)
                    .padding(null, 50, null, null)
                    .append(GProgressBar(activity))

//            container.addView(GProgressBar(activity))

        }
    }
}
