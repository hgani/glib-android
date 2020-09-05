package com.glib.core.ui.json.views.panels

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.panel.GVerticalPanel

//class Column(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
//    private val panel = GVerticalPanel(context)
//
//    override fun initView(): View {
//        JsonUi.initVerticalPanel(panel, spec, screen, fragment)
//        return panel
//    }
//}

class Column(spec: GJson, screen: GActivity, fragment: GFragment): Default(spec, screen, fragment) {
    override fun initView(): View {
        super.initView()
        return panel.width(ViewGroup.LayoutParams.MATCH_PARENT)
    }
}
