package com.glib.core.ui.json.views.fields

import android.graphics.Color
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.Ui
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.json.views.panels.Form
import com.glib.core.ui.view.GEditText
import com.glib.core.ui.view.GTextInputEditText
import com.glib.core.ui.view.GTextInputLayout
import com.google.android.material.textfield.TextInputLayout
import java.lang.IllegalStateException
import java.lang.RuntimeException

abstract class AbstractField(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment), SubmittableField {
////    protected val view = GEditText(context)
//
//    protected val view = GTextInputLayout(context)
////    .append(editText)
////    protected val editText = GTextInputEditText(view.context).width(ViewGroup.LayoutParams.MATCH_PARENT)
//    protected val editText by lazy {
//        GTextInputEditText(view.context).width(ViewGroup.LayoutParams.MATCH_PARENT)
//    }
//
//    final override var name: String? = null
//        private set
//
//    override val value: String
//        get() = editText.text.toString()
//
//    override fun initView(): View {
////        view.gravity = Gravity.TOP or Gravity.START
////        editText.gravity = Gravity.TOP or Gravity.START
//
//        editText.padding(null, 18, null, 18)
//
//        this.name = spec["name"].string
//
//        editText.text(spec["value"].stringValue)
//
//        // TODO: Move to glib wrapper
////        view.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
//
//        // TODO: Only do this if styleClass includes outline
//        view.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
//        view.setBoxCornerRadii(CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS)
//
//        view.boxBackgroundColor = Color.TRANSPARENT
//
////        view.isErrorEnabled = true
//        view.isErrorEnabled = false
//
//        view.hint = spec["label"].stringValue
//
////        spec["placeholder"].string?.let {
////            editText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
////                if (hasFocus) {
////                    editText.setHint(it)
////                } else {
////                    editText.setHint(null)
////                }
////            }
////        }
//
//        return view.append(editText)
//    }

    final override var name: String? = null
        private set

    override fun onAfterInitView(view: View) {
        name = spec["name"].string

        GLog.t(javaClass, "onAfterInitView1")

        registerToClosestForm(view)

//        processJsonLogic(view: view)
    }

    fun registerToClosestForm(field: View) {
        GLog.t(javaClass, "registerToClosestForm1")
//
//        Ui.exec {
//
//        }

        closest(Form.FormPanel::class.java, field)?.let { panel ->
            GLog.t(javaClass, "registerToClosestForm2")

            val jsonField = this as? SubmittableField
            if (jsonField != null) {
                GLog.t(javaClass, "registerToClosestForm3")
                panel.addField(jsonField)
            } else {
                throw IllegalStateException("Not a JSON field")
            }
        }

//        if let form = closest(JsonView_Panels_Form.FormPanel.self, from: field) {
//            if let jsonField = self as? SubmittableField {
//                form.addField(jsonField)
//            } else {
//                fatalError("Not a JSON field")
//            }
//        }
    }

}
