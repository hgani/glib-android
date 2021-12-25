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

//const val CORNER_RADIUS = 16f

abstract class AbstractText(spec: GJson, screen: GActivity, fragment: GFragment): AbstractField(spec, screen, fragment), SubmittableField {
    protected val view = GTextInputLayout(context)
//    .append(editText)
//    protected val editText = GTextInputEditText(view.context).width(ViewGroup.LayoutParams.MATCH_PARENT)
    protected val editText by lazy {
        GTextInputEditText(view.context).width(ViewGroup.LayoutParams.MATCH_PARENT)
    }
//
//    final override var name: String? = null
//        private set

    override val value: String
        get() = editText.text.toString()

    override fun initView(): View {
//        view.gravity = Gravity.TOP or Gravity.START
//        editText.gravity = Gravity.TOP or Gravity.START

        editText.padding(null, 18, null, 18)

//        this.name = spec["name"].string

        editText.text(spec["value"].stringValue)

        // TODO: Move to glib wrapper
//        view.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);

//        // TODO: Only do this if styleClass includes outline
//        view.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
//        view.setBoxCornerRadii(CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS)

        view.boxBackgroundColor = Color.TRANSPARENT

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

        return view.append(editText)
    }

    override fun applyStyleClass(styleClass: String) {
//        super.applyStyleClass(styleClass)

        GLog.t(javaClass, "applyStyleClass1")

        if (styleClass == "outlined") {
            GLog.t(javaClass, "applyStyleClass2")
            view.outlined()

//            view.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
//            view.setBoxCornerRadii(CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS)
        }
    }
}
