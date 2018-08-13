package com.gani.lib.ui.json

import android.content.Context
import android.view.View
import com.gani.lib.json.GJsonObject
import com.gani.lib.logging.GLog
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.view.IView

abstract class JsonView(val spec: GJsonObject<*, *>, val screen: GActivity) {
    val context: Context
        get() = screen.context

    fun createView(): View {
        val view = initView()
        initGenericAttributes(view)
        return view
    }

    fun initGenericAttributes(backend: View) {
        (backend as? IView).let {
            // TODO: set bg color
        }
    }

    abstract fun initView(): View

    companion object {
        fun create(spec: GJsonObject<*, *>, screen: GActivity): JsonView? {
            val klass = JsonUi.loadClass(spec["view"].stringValue, JsonView::class.java)
            val constructor = klass?.getConstructor(GJsonObject::class.java, GActivity::class.java)
            if (constructor != null) {
                return constructor.newInstance(spec, screen)
            }
            GLog.w(JsonView::class.java, "Failed loading view: $spec")
            return null
        }
    }
}