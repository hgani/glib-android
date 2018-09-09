package com.gani.lib.ui.json.views.fields

import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.view.GEditText

abstract class AbstractTextV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    protected val view = GEditText(context)

    override fun initView(): View {
        view.hint(spec["label"].stringValue)
        return view
    }
}
