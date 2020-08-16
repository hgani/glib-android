package com.glib.core.ui.json.views

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.icon.GIcon
import com.glib.core.ui.json.JsonUi
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.view.GImageView
import com.glib.core.ui.view.GView

class Icon(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val view = GImageView(context)

    override fun initView(): View {
        JsonUi.iconDrawable(spec["spec"])?.let {
            view.source(it)
        }
        return view
    }
}
