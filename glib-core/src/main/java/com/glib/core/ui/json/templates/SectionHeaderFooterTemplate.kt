package com.glib.core.ui.json.templates

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonTemplate
import com.glib.core.ui.json.views.panels.TemplateHolder
import com.glib.core.ui.list.templates.SectionHeaderFooterHolder

class SectionHeaderFooterTemplate(spec: GJson, screen: GActivity, val fragment: GFragment): JsonTemplate(spec, screen) {
    // Generate a unique ID per header/footer because they should not get reused.
    override val viewType: Int = View.generateViewId()

    override fun createHolder(): TemplateHolder {
        return SectionHeaderFooterHolder(screen, screen, fragment, spec)
    }
}
