package com.glib.core.ui.json.views.fields

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.view.GEditText

abstract class AbstractText(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment), SubmittableField {
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
