package com.glib.core.ui.list.templates

import android.content.Context
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonTemplate
import com.glib.core.ui.json.views.panels.TemplateHolder
import com.glib.core.ui.json.views.panels.VerticalV1
import com.glib.core.ui.list.DtoRecyclerAdapter
import com.glib.core.ui.view.GTextView

class SectionHeaderFooterHolder(context: Context, val screen: GActivity, val fragment: GFragment, val spec: GJson) : TemplateHolder(context, false) {
    init {
        VerticalV1(container, spec, screen, fragment).createView()
    }

    override fun update(item: JsonTemplate) {
        // Initialization should be done in constructor once only, because this holder is not supposed to get reused.
    }
}