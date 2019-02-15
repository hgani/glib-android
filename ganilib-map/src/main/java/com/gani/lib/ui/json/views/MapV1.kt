package com.gani.lib.ui.json.views

import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonView
import com.gani.map.GMapView


class MapV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val map = GMapView(context)

    override fun initView(): View {
        // TODO
        return map
    }
}
