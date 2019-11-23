package com.glib.core.ui.json

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.json.GJsonObject
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.ProgressIndicator
import com.glib.core.ui.view.IView
import com.glib.core.utils.Res

abstract class JsonView(val spec: GJson, val screen: GActivity, val fragment: GFragment): ProgressIndicator {
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
            "matchParent" -> view.height(ViewGroup.LayoutParams.MATCH_PARENT)
            "wrapContent" -> view.height(ViewGroup.LayoutParams.WRAP_CONTENT)
            else -> view.height(spec["height"].int)
        }
    }

    private fun initPadding(view: IView) {
        val padding = spec["padding"]
        view.padding(padding["left"].int, padding["top"].int, padding["right"].int, padding["bottom"].int)
    }

    abstract fun initView(): View

//    fun updateStatus(busy: Boolean) {
//        // To be overridden
//    }

    override fun showProgress() {
        // To be overridden
    }

    override fun hideProgress() {
        // To be overridden
    }

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