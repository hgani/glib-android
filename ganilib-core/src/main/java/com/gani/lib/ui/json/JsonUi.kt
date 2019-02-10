package com.gani.lib.ui.json

import android.graphics.Color
import android.graphics.drawable.Drawable
import com.gani.lib.json.GJson
import com.gani.lib.logging.GLog
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.icon.GIcon
import com.gani.lib.ui.json.views.panels.VerticalV1
import com.gani.lib.ui.panel.GVerticalPanel
import com.gani.lib.utils.Res
import com.mikepenz.iconics.IconicsDrawable

class JsonUi {
    companion object {
        fun parseScreenContent(spec: GJson, fragment: GFragment) {
            val screen = fragment.gActivity
            val container = fragment.container
            if (screen == null || container == null) {
                return
            }

            if (!JsonAction.execute(spec["onResponse"], screen, null)) {
                initVerticalPanel(container.header, spec["header"], screen, fragment)
                initVerticalPanel(container.content, spec["content"], screen, fragment)
                initVerticalPanel(container.footer, spec["footer"], screen, fragment)
                JsonAction.execute(spec["onLoad"], screen, null)
            }
        }

        fun parseScreenAction(spec: GJson) {
            JsonBgAction.executeAll(spec["onLoad"])
        }

        fun parseResponse(spec: GJson, fragment: GFragment) {
            val screen = fragment.gActivity
            if (screen == null) {
                return
            }
            JsonAction.execute(spec["onResponse"], screen, null)
        }

        private fun initVerticalPanel(panel: GVerticalPanel, spec: GJson, screen: GActivity, fragment: GFragment) {
            VerticalV1(panel, spec, screen, fragment).createView()
        }

        fun <T> loadClass(name: String, type: Class<T>, namespace: String): Class<T>? {
            val substrings = name.split("/")
            val prefix = substrings.dropLast(1).joinToString(".")
            val className = substrings.lastOrNull()?.replace("-v", "V")?.capitalize()
            val prefixedName = if (prefix.length > 0) "$prefix.$className" else className
            val qualifiedName = "com.gani.lib.ui.json.$namespace.$prefixedName"
            GLog.v(JsonUi::class.java, "Loading $qualifiedName from $name")
            try {
                return Class.forName(qualifiedName) as? Class<T>
            } catch (ex: Exception) {
                return null
            }
        }

        fun iconDrawable(spec: GJson): Drawable? {
            spec["materialName"].string?.let { iconName ->
                return IconicsDrawable(Res.context, "gmd-${iconName}").sizeDp(GIcon.ACTION_BAR_SIZE).color(Color.BLUE)
            }
            return null
        }
    }
}