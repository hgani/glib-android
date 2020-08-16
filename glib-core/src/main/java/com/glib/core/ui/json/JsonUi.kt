package com.glib.core.ui.json

import android.graphics.drawable.Drawable
import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.icon.GIcon
import com.glib.core.ui.json.views.panels.Vertical
import com.glib.core.ui.panel.GVerticalPanel
import com.glib.core.utils.Res
import com.mikepenz.iconics.IconicsDrawable

class JsonUi {
    companion object {
        fun parseScreenContent(spec: GJson, fragment: GFragment) {
            val screen = fragment.gActivity
            val container = fragment.container
            if (screen == null || container == null) {
                return
            }

            spec["title"].string?.let {
                // In this case, screen.title doesn't work
                screen.navBar.title = it
            }

            if (!JsonAction.execute(spec["onResponse"], screen, null, null)) {
                resetVerticalPanel(container.header, spec["header"], screen, fragment)
                resetVerticalPanel(container.content, spec["body"], screen, fragment)
                resetVerticalPanel(container.footer, spec["footer"], screen, fragment)
                JsonAction.execute(spec["onLoad"], screen, null, null)

                // Check for presence to prevent the panel getting cleared when not applicable
                // Legacy
                spec["content"].presence?.let {
                    resetVerticalPanel(container.content, it, screen, fragment)
                }
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
            JsonAction.execute(spec["onResponse"], screen, null, null)
        }

        private fun resetVerticalPanel(panel: GVerticalPanel, spec: GJson, screen: GActivity, fragment: GFragment) {
            panel.removeAllViews()
            initVerticalPanel(panel, spec, screen, fragment)
        }

        fun initVerticalPanel(panel: GVerticalPanel, spec: GJson, screen: GActivity, fragment: GFragment) {
            Vertical(panel, spec, screen, fragment).createView()
        }

        fun <T> loadClass(name: String, type: Class<T>, namespace: String): Class<T>? {
            val substrings = name.removeSuffix("-v1").split("/")
            val prefix = substrings.dropLast(1).joinToString(".")
            val className = substrings.lastOrNull()?.replace("-v", "V")?.capitalize()
            val prefixedName = if (prefix.length > 0) "$prefix.$className" else className
            val qualifiedName = "com.glib.core.ui.json.$namespace.$prefixedName"
            GLog.v(JsonUi::class.java, "Loading $qualifiedName from $name")
            try {
                return Class.forName(qualifiedName) as? Class<T>
            } catch (ex: Exception) {
                return null
            }
        }

        fun iconDrawable(spec: GJson): Drawable? {
//            spec["materialName"].string?.let { iconName ->
//                return IconicsDrawable(Res.context, "gmd-${iconName}").sizeDp(GIcon.ACTION_BAR_SIZE)
//            }
            spec["material"]["name"].string?.let { iconName ->
                GLog.i(JsonUi::class.java, "Icon not found: ${iconName}")
                return IconicsDrawable(Res.context, "gmd-${iconName}").sizeDp(GIcon.ACTION_BAR_SIZE)
            }
            return null
        }
    }
}