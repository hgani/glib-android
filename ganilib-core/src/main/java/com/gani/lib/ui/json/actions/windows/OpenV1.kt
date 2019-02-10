package com.gani.lib.ui.json.actions.windows

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import com.gani.lib.BuildConfig
import com.gani.lib.http.GRestCallback
import com.gani.lib.http.Rest
import com.gani.lib.json.GJson
import com.gani.lib.logging.GLog
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.screen.GScreenContainer
import com.gani.lib.screen.NavHelper
import com.gani.lib.ui.json.JsonAction
import com.gani.lib.ui.json.JsonUi
import com.gani.lib.ui.menu.GMenu

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

        initNavigation()
    }

    override fun createNewIntentFragment(): GFragment {
        return ContentFragment()
    }

    private fun initNavigation() {
        nav.showHomeIcon()
    }



    class ContentFragment : JsonUiFragment() {
        override fun initContent(activity: GActivity, container: GScreenContainer) {
            super.path = (args[ARG_URL] as? String)
            super.prependHost = false
            super.parseMenu = true
            super.initContent(activity, container)
        }

    }
}

abstract class JsonUiFragment : GFragment {
    protected var path: String? = null
    protected var prependHost: Boolean = false
    protected var parseMenu = true
    private var page: GJson? = null

    constructor() {
        // Mandatory constructor
    }

    protected constructor(path: String, prependHost: Boolean) {
        this.path = path
        this.prependHost = prependHost
    }

    override fun onRefresh() {
        super.onRefresh()

        val callback = GRestCallback.Default(this@JsonUiFragment) { response ->
            JsonUi.parseScreenContent(response.result, this@JsonUiFragment)
            page = response.result
            activity?.invalidateOptionsMenu()
        }
        path?.let { path ->
            Rest.GET.async(path, null, prependHost).execute(callback)
        }
    }

    override fun initContent(activity: GActivity, container: GScreenContainer) {
        onRefresh()

        (args[JsonUiScreen.ARG_SPEC] as? GJson)?.let {
            JsonAction.execute(it, activity, null)
            activity.finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        val page = this.page
        val activity = this.gActivity
        if (page == null || activity == null || menu == null) {
            return
        }

        val gMenu = GMenu(menu)
        if (parseMenu) {
            page["rightNavButtons"].arrayValue.forEach { button ->
                val name = button["icon"]["materialName"].stringValue
                gMenu.add(name) { item ->
                    item.showIfRoom().onClick {
                        JsonAction.execute(button["onClick"], activity, null)
                    }
                    JsonUi.iconDrawable(button["icon"])?.let { drawable ->
                        item.icon(drawable)
                    }
                }
            }
        }

        if (BuildConfig.DEBUG) {
            gMenu.add("?") { item ->
                item.showIfRoom().onClick {
                    launch.alert(page.toString())
                }
            }
        }
    }
}