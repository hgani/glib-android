package com.glib.core.ui.json.views

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonView
import com.glib.map.GMapView


class MapV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val map = GMapView(context).attachTo(fragment)

    override fun initView(): View {
        return map
    }
}
