package com.gani.lib.ui.json

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.gani.lib.json.GJson
import com.gani.lib.json.GJsonObject
import com.gani.lib.logging.GLog
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.view.IView
import com.gani.lib.utils.Res

abstract class JsonView(val spec: GJson, val screen: GActivity, val fragment: GFragment) {
    val context: Context
        get() = screen.context

    fun createView(): View {
        val view = initView()
        initGenericAttributes(view)
        return view
    }

    fun initGenericAttributes(backend: View) {
        (backend as? IView)?.let {
            initBackgroundColor(it)
            initWidth(it)
            initHeight(it)
            initPadding(it)
        }
    }

    private fun initBackgroundColor(view: IView) {
        spec["backgroundColor"].string?.let {
            val color = when (it) {
                "transparent" -> Color.TRANSPARENT
                else -> Res.color(it)
            }
            view.bgColor(color)
        }
    }

    private fun initWidth(view: IView) {
        when (spec["width"].stringValue) {
            "matchParent" -> view.width(ViewGroup.LayoutParams.MATCH_PARENT)
            "wrapContent" -> view.width(ViewGroup.LayoutParams.WRAP_CONTENT)
            else -> view.width(spec["width"].int)
        }
    }

    private fun initHeight(view: IView) {
        when (spec["height"].stringValue) {
            "matchParent" -> view.width(ViewGroup.LayoutParams.MATCH_PARENT)
            "wrapContent" -> view.width(ViewGroup.LayoutParams.WRAP_CONTENT)
            else -> view.height(spec["height"].int)
        }
    }

    private fun initPadding(view: IView) {
        val padding = spec["padding"]
        view.padding(padding["left"].int, padding["top"].int, padding["right"].int, padding["bottom"].int)
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