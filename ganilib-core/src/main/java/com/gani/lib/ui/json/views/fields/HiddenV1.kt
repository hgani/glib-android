package com.gani.lib.ui.json.views.fields

import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment

class HiddenV1(spec: GJson, screen: GActivity, fragment: GFragment): AbstractTextV1(spec, screen, fragment) {
    override fun initView(): View {
        view.visibility = View.GONE
        super.initView()
        return view
    }
}
