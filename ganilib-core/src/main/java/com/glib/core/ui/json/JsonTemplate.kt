package com.glib.core.ui.json

import android.content.Context
import com.glib.core.json.GJson
import com.glib.core.json.GJsonObject
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.views.panels.TemplateHolder

abstract class JsonTemplate(val spec: GJson, val screen: GActivity) {
    val context: Context
        get() = screen.context

    abstract val viewType: Int

    abstract fun createHolder(): TemplateHolder

    companion object {
        fun create(spec: GJson, screen: GActivity): JsonTemplate? {
            val klass = JsonUi.loadClass(spec["template"].stringValue, JsonTemplate::class.java, "templates")
            val constructor = klass?.getConstructor(GJsonObject::class.java, GActivity::class.java)
            if (constructor != null) {
                return constructor.newInstance(spec, screen)
            }
            GLog.w(JsonTemplate::class.java, "Failed loading template: $spec")
            return null
        }
    }
}