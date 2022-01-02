package com.glib.core.ui.json.views.panels

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.panel.GVerticalPanel
import com.glib.core.ui.view.GPdfView
import com.glib.core.ui.view.GWebView
import com.glib.core.ui.view.GWeightable

class Web(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    override fun initView(): View {
        val url = spec["url"].stringValue

        val view: View
        // Need to be improved to cover all scenarios
        if (url.endsWith(".pdf")) {
            view = GPdfView(context)
            view.load(url)
        } else {
            view = GWebView(context, screen.circularProgressIndicator)
            view.load(url)
        }
        return view
    }
}
