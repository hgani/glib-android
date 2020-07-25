package com.glib.core.ui.json

import android.content.Context
import android.view.View
import com.glib.core.json.GJson
import com.glib.core.json.GJsonObject
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity

abstract class JsonAction(val spec: GJson, val screen: GActivity) {
    var targetView: View? = null
    var targetController: JsonView? = null

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
            if (spec.isNull()) {
                return null
            }

            val klass = JsonUi.loadClass(spec["action"].stringValue, JsonAction::class.java, "actions")
            val constructor = klass?.getConstructor(GJsonObject::class.java, GActivity::class.java)
            if (constructor != null) {
                return constructor.newInstance(spec, screen)
            }
            GLog.w(JsonAction::class.java, "Failed loading action: $spec")
            return null
        }

        fun execute(spec: GJson, screen: GActivity, creator: View?, controller: JsonView?): Boolean {
            create(spec, screen)?.let {
                it.targetView = creator
                it.targetController = controller
                it.execute()
                return true
            }
            return false
        }

        fun execute(spec: GJson, screen: GActivity, creator: JsonAction) {
            create(spec, screen)?.let {
                it.targetView = creator.targetView
                it.targetController = creator.targetController
                it.execute()
            }
        }
    }
}