package com.gani.lib.ui.json

import android.content.Context
import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.json.GJsonObject
import com.gani.lib.logging.GLog
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.view.IView

abstract class JsonView(val spec: GJson, val screen: GActivity, val fragment: GFragment) {
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
        fun create(spec: GJson, screen: GActivity, fragment: GFragment): JsonView? {
            val klass = JsonUi.loadClass(spec["view"].stringValue, JsonView::class.java, "views")
            val constructor = klass?.getConstructor(GJsonObject::class.java, GActivity::class.java, GFragment::class.java)
            if (constructor != null) {
                return constructor.newInstance(spec, screen, fragment)
            }
            GLog.w(JsonView::class.java, "Failed loading view: $spec")
            return null
        }
    }
}