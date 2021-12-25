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
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.view.GEditText
import com.glib.core.ui.view.GTextInputEditText
import com.glib.core.ui.view.GTextInputLayout
import com.google.android.material.textfield.TextInputLayout
import java.lang.RuntimeException

abstract class AbstractText(spec: GJson, screen: GActivity, fragment: GFragment): AbstractField(spec, screen, fragment), SubmittableField {
    protected val view = GTextInputLayout(context)
//    .append(editText)
//    protected val editText = GTextInputEditText(view.context).width(ViewGroup.LayoutParams.MATCH_PARENT)
    protected val editText by lazy {
        GTextInputEditText(view.context).width(ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override val value: String
        get() = editText.text.toString()

    override fun initView(): View {
//        view.gravity = Gravity.TOP or Gravity.START
//        editText.gravity = Gravity.TOP or Gravity.START

        editText.padding(null, 18, null, 18)

        editText.text(spec["value"].stringValue)

        // TODO: Move to glib wrapper
//        view.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);

//        // TODO: Only do this if styleClass includes outline
//        view.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
//        view.setBoxCornerRadii(CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS)

//        view.boxBackgroundColor = Color.TRANSPARENT
//
//        view.outlined()

//        view.isErrorEnabled = true
        view.isErrorEnabled = false

        view.hint = spec["label"].stringValue

        spec["placeholder"].string?.let {
            editText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    editText.setHint(it)
                } else {
                    editText.setHint(null)
                }
            }
        }

        if (styleClasses.contains("outlined")) {
            // outlined() has to be called before append()
            view.outlined()
        }

        return view.append(editText)
    }

    override fun applyStyleClass(styleClass: String) {

//        view.outlined()
//        super.applyStyleClass(styleClass)

        GLog.t(javaClass, "applyStyleClass1: ${styleClass}")

//        if (styleClass == "outlined") {
//            GLog.t(javaClass, "applyStyleClass2")
//            view.outlined()
//
////            view.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
////            view.setBoxCornerRadii(CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS)
//        }

//        if (styleClass.equals("outlined")) {
//            GLog.t(javaClass, "applyStyleClass3")
//            view.outlined()
//
////            view.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
////            view.setBoxCornerRadii(CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS)
//        }
    }
}
