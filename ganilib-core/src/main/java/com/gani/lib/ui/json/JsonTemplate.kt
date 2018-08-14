package com.gani.lib.ui.json

import android.content.Context
import com.gani.lib.json.GJson
import com.gani.lib.json.GJsonObject
import com.gani.lib.logging.GLog
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.views.panels.TemplateHolder

abstract class JsonTemplate(val spec: GJson, val screen: GActivity) {
    val context: Context
        get() = screen.context

    abstract val viewType: Int

    abstract fun createHolder(): TemplateHolder

    companion object {
        fun create(spec: GJson, screen: GActivity): JsonTemplate? {
            val klass = JsonUi.loadClass(spec["template"].stringValue, JsonTemplate::class.java)
            val constructor = klass?.getConstructor(GJsonObject::class.java, GActivity::class.java)
            if (constructor != null) {
                return constructor.newInstance(spec, screen)
            }
            GLog.w(JsonTemplate::class.java, "Failed loading template: $spec")
            return null
        }
    }
}