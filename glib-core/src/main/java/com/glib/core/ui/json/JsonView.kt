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
import com.glib.core.ui.Ui
import com.glib.core.ui.json.views.panels.Form
import com.glib.core.ui.view.IView
import com.glib.core.utils.Res

abstract class JsonView(val spec: GJson, val screen: GActivity, val fragment: GFragment): ProgressIndicator {
    val context: Context
        get() = screen.context

    fun <T: View> closest(instanceType: Class<T>, from: View): T? {
        var ancestor = from?.parent
        while (ancestor != null && !instanceType.isInstance(ancestor)) {
            ancestor = ancestor.parent
        }
        return instanceType.cast(ancestor)

//        return (ancestor as? instanceType)
//        (ancestor as? Form.FormPanel)?.submit(controller)

//
//        var ancestor = from?.parent
//        while (ancestor != null && !(ancestor is Form.FormPanel)) {
//            ancestor = ancestor.parent
//        }
//        (ancestor as? Form.FormPanel)?.submit(controller)

//        from.pare
//        if let superview = from.superview {
//            if let found = superview as? T {
//                return found
//            } else {
//                return closest(instanceType, from: superview)
//            }
//        }
//        return nil
    }

    // TODO: Rename to initView()
    fun createView(): View {
        val view = initView()
        initGenericAttributes(view)

        // This might look hacky but it's much less error prone than the alternative, where we need to
        // explicitly execute the callback for every parent component, which will get very complex
        // when it involves nested parents because we won't know at which point the whole nesting has
        // been fully connected.
        Ui.exec {
            GLog.t(javaClass, "createView1")
            // This is supposed to execute after the view has been added to parent.
            this.onAfterInitView(view)
        }
//        DispatchQueue.main.async {
//            // This is supposed to execute after the view has been added to parent.
//            self.onAfterInitView(view)
//            self.registerJsonLogic(view: view)
//        }

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

    open protected fun initBackgroundColor(view: IView) {
        ifColor(spec["backgroundColor"].string) {
            view.bgColor(it)
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

    open fun onAfterInitView(view: View) {
        // To be overridden
    }

    override fun showProgress() {
        // To be overridden
    }

    override fun hideProgress() {
        // To be overridden
    }

    fun styleClasses(): List<String> {
        return spec["styleClasses"].arrayValue.map { it.stringValue }
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

        fun ifColor(colorName: String?, handler: (Int) -> Unit) {
            colorName?.let {
                val color = when (it) {
                    "transparent" -> Color.TRANSPARENT
                    else -> Res.color(it)
                }
                handler(color)
            }
        }
    }
}