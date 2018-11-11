package com.gani.lib.ui.json.actions.screens

import android.content.Intent
import android.os.Bundle
import com.gani.lib.http.GRestCallback
import com.gani.lib.http.GRestResponse
import com.gani.lib.http.Rest
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.screen.GScreenContainer
import com.gani.lib.screen.NavHelper
import com.gani.lib.ui.json.JsonAction
import com.gani.lib.ui.json.JsonUi

class OpenV1(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        val url = spec["url"].string
        if (url == null) {
            return false
        }
        screen.startActivity(JsonUiScreen.intent(url))
        return true
    }
}

class JsonUiScreen : GActivity() {
    companion object {
        val ARG_URL = "url"
        val ARG_SPEC = "spec"

        fun intent(url: String): Intent {
            return intentBuilder(JsonUiScreen::class)
                    .withArg(ARG_URL, url).intent
        }

        fun intent(actionSpec: GJson): Intent {
            return intentBuilder(JsonUiScreen::class)
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    .withArg(ARG_SPEC, actionSpec).intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = NavHelper(this)
        super.onCreateForScreen(savedInstanceState, view)
        setFragmentWithToolbar(createNewIntentFragment(), false, savedInstanceState)
    }

    override fun createNewIntentFragment(): GFragment {
        return ContentFragment()
    }



    class ContentFragment : GFragment() {
        override fun initContent(activity: GActivity, container: GScreenContainer) {
            (args[ARG_URL] as? String)?.let {
                val callback = object : GRestCallback.Default(this@ContentFragment) {
                    override fun onRestResponse(response: GRestResponse) {
                        super.onRestResponse(response)
                        JsonUi.parseScreen(response.result, this@ContentFragment)
                    }
                }
                Rest.GET.asyncUrl(it, null, callback = callback).execute();
            }

            (args[ARG_SPEC] as? GJson)?.let {
                JsonAction.execute(it, activity, null)
                activity.finish()
            }
        }
    }
}