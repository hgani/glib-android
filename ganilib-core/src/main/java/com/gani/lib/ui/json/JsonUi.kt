package com.gani.lib.ui.json

import com.gani.lib.json.GJsonObject
import com.gani.lib.logging.GLog
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.views.panels.VerticalV1
import com.gani.lib.ui.layout.GLinearLayout
import kotlinx.android.synthetic.main.common_fragment.*

class JsonUi {
    companion object {
        fun parse(spec: GJsonObject<*, *>, screen: GActivity) {
            initVerticalPanel(screen.container.header, spec["header"], screen)
            initVerticalPanel(screen.container.content, spec["content"], screen)
            initVerticalPanel(screen.container.footer, spec["footer"], screen)
            JsonAction.executeAll(spec["onLoad"], screen)
        }

        private fun initVerticalPanel(panel: GLinearLayout, spec: GJsonObject<*, *>, screen: GActivity) {
            VerticalV1(panel, spec, screen).createView()
        }

        fun <T> loadClass(name: String, type: Class<T>): Class<T>? {
            val typeName = when (type) {
                JsonView::class.java -> "views"
                JsonAction::class.java -> "actions"
                else -> ""
            }

            val substrings = name.split("/")
            val prefix = substrings.dropLast(1).joinToString(".")
            val className = substrings.lastOrNull()?.replace("-v", "V")?.capitalize()
            val qualifiedName = "com.gani.lib.ui.json.$typeName.$prefix.$className"
            GLog.t(JsonUi::class.java, "Loading $qualifiedName from $name")
            try {
                return Class.forName(qualifiedName) as? Class<T>
            } catch (ex: Exception) {
                return null
            }
        }
    }
}