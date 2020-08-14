package com.glib.core.ui.list.templates

import android.content.Context
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonTemplate
import com.glib.core.ui.json.views.panels.TemplateHolder
import com.glib.core.ui.json.views.panels.Vertical

class SectionHeaderFooterHolder(context: Context, val screen: GActivity, val fragment: GFragment, val spec: GJson) : TemplateHolder(context, false) {
    init {
        Vertical(container, spec, screen, fragment).createView()
    }

    override fun update(item: JsonTemplate) {
        // Initialization should be done in constructor once only, because this holder is not supposed to get reused.
    }
}