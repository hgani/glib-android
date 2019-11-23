package com.glib.core.ui.json.views.fields

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment

class HiddenV1(spec: GJson, screen: GActivity, fragment: GFragment): AbstractTextV1(spec, screen, fragment) {
    override fun initView(): View {
        view.visibility = View.GONE
        super.initView()
        return view
    }
}
