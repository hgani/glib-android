package com.gani.lib.ui.json.views.fields

import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.view.GEditText

abstract class AbstractTextV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment), SubmittableField {
    protected val view = GEditText(context)

    final override var name: String? = null
        private set

    override val value: String
        get() = view.text.toString()

    override fun initView(): View {
        this.name = spec["name"].string

        view.hint(spec["label"].stringValue)
        view.text(spec["value"].stringValue)

        return view
    }
}
