package com.gani.lib.ui.json

import com.gani.lib.json.GJsonObject
import com.gani.lib.logging.GLog
import com.gani.lib.screen.GActivity

class JsonUi {
    companion object {
        fun parse(result: GJsonObject<*, *>, screen: GActivity) {
            JsonAction.executeAll(result["onLoad"], screen)
        }

        fun <T> loadClass(name: String, type: Class<T>): Class<T>? {
            val typeName = when (type) {
                JsonAction::class.java -> "actions"
                else -> ""
            }

            val substrings = name.split("/")
            val prefix = substrings.dropLast(1).joinToString(".")
            val className = substrings.lastOrNull()?.replace("-v", "V")?.capitalize()
            val qualifiedName = "com.gani.lib.ui.json.${typeName}.$prefix.$className"
            GLog.t(JsonUi::class.java, "Loading $qualifiedName from $name")
            try {
                return Class.forName(qualifiedName) as? Class<T>
            } catch (ex: Exception) {
                return null
            }
        }
    }
}