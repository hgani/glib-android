package com.glib.core.ui.json.views.fields

import android.graphics.Color
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.view.GEditText
import com.glib.core.ui.view.GTextInputEditText
import com.glib.core.ui.view.GTextInputLayout
import com.google.android.material.textfield.TextInputLayout

const val CORNER_RADIUS = 16f

abstract class AbstractText(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment), SubmittableField {
//    protected val view = GEditText(context)

    protected val view = GTextInputLayout(context)
//    .append(editText)
//    protected val editText = GTextInputEditText(view.context).width(ViewGroup.LayoutParams.MATCH_PARENT)
    protected val editText by lazy {
        GTextInputEditText(view.context).width(ViewGroup.LayoutParams.MATCH_PARENT)
    }

    final override var name: String? = null
        private set

    override val value: String
        get() = editText.text.toString()

    override fun initView(): View {
        this.name = spec["name"].string

        editText.text(spec["value"].stringValue)

        // TODO: Move to glib
//        view.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);

        view.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        view.setBoxCornerRadii(CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS)

        view.boxBackgroundColor = Color.TRANSPARENT

//        view.isErrorEnabled = true
        view.isErrorEnabled = false

        view.hint = spec["label"].stringValue
//        view.hint = "Test label"

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
}
