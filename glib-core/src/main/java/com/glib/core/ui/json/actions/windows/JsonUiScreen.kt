package com.glib.core.ui.json.actions.windows

import android.content.Intent
import android.os.Bundle
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.screen.GScreen
import com.glib.core.screen.GScreenContainer

class JsonUiScreen : GScreen() {
    companion object {
        val ARG_PATH = "path"
        val ARG_SPEC = "spec"
        val ARG_ROOT = "isRoot"
        val ARG_PREPEND_HOST = "prependHost"

        fun intent(path: String, isRoot: Boolean = false, prependHost: Boolean = true): Intent {
            return intentBuilder(JsonUiScreen::class)
                    .withArg(ARG_PATH, path)
                    .withArg(ARG_PREPEND_HOST, prependHost)
                    .withArg(ARG_ROOT, isRoot).intent
        }

        fun intent(actionSpec: GJson): Intent {
            return intentBuilder(JsonUiScreen::class)
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    .withArg(ARG_SPEC, actionSpec).intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.onCreateForScreen(savedInstanceState)
        setFragmentWithToolbar(createNewIntentFragment(), true, savedInstanceState)
        initNavigation()
    }

    override fun createNewIntentFragment(): GFragment {
        return ContentFragment()
    }

    private fun initNavigation() {
        if (!args[ARG_ROOT].boolValue) {
            nav.showHomeIcon()
        }
    }




    class ContentFragment : JsonUiFragment() {
        override fun initContent(activity: GActivity, container: GScreenContainer) {
            setPath(args[ARG_PATH].string, args[ARG_PREPEND_HOST].boolValue)
//            super.path = args[ARG_URL].string
//            super.prependHost = false
            super.parseMenu = true
            super.initContent(activity, container)
        }

    }
}
