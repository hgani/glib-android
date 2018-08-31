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
import com.gani.lib.screen.GScreenView
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
        fun intent(url: String): Intent {
            return intentBuilder(JsonUiScreen::class).withArg(url).intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = GScreenView(this)
        super.onCreateForScreen(savedInstanceState, view)
        setFragmentWithToolbar(createNewIntentFragment(), false, savedInstanceState)
    }

    override fun createNewIntentFragment(): GFragment {
        return ContentFragment()
    }



    class ContentFragment : GFragment() {
        override fun initContent(activity: GActivity, container: GScreenContainer) {
            val callback = object : GRestCallback.Default(this@ContentFragment) {
                override fun onRestResponse(response: GRestResponse) {
                    super.onRestResponse(response)
                    JsonUi.parse(response.result, this@ContentFragment)
                }
            }
            (args.single as? String)?.let {
                Rest.GET.asyncUrl(it, null, callback = callback).execute();
            }
        }
    }
}