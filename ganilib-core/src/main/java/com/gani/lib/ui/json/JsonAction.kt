package com.gani.lib.ui.json

import android.content.Context
import com.gani.lib.json.GJson
import com.gani.lib.json.GJsonObject
import com.gani.lib.logging.GLog
import com.gani.lib.screen.GActivity

abstract class JsonAction(val spec: GJson, val screen: GActivity) {
    val context: Context
        get() = screen.context

    fun execute() {
        if (!silentExecute()) {
            GLog.w(javaClass, "Invalid action spec: $spec")
        }
    }

    abstract fun silentExecute(): Boolean

    companion object {
        private fun create(spec: GJson, screen: GActivity): JsonAction? {
            val klass = JsonUi.loadClass(spec["action"].stringValue, JsonAction::class.java, "actions")
            val constructor = klass?.getConstructor(GJsonObject::class.java, GActivity::class.java)
            if (constructor != null) {
                return constructor.newInstance(spec, screen)
            }
            GLog.w(JsonAction::class.java, "Failed loading action: $spec")
            return null
        }

        fun executeAll(spec: GJson, screen: GActivity) {
            spec.arrayValue.forEach {
                create(it, screen)?.execute()
            }
        }
    }
}