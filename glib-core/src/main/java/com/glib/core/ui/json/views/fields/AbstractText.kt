package com.glib.core.ui.json.views.fields

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.view.GTextInputEditText
import com.glib.core.ui.view.GTextInputLayout

abstract class AbstractText(spec: GJson, screen: GActivity, fragment: GFragment): AbstractField(spec, screen, fragment), SubmittableField {
    protected val view = GTextInputLayout(context)
    protected val editText by lazy {
        GTextInputEditText(view.context).width(ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override val value: String
        get() = editText.text.toString()

    override fun initView(): View {
        editText.padding(null, 18, null, 18)
        editText.text(spec["value"].stringValue)

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
            // outlined() has to be called before append() otherwise the outline styling will
            // look incomplete.
            view.outlined()
        }

        initOnFocusBorderColor()

        return view.append(editText)
    }

    fun initOnFocusBorderColor() {
        // Avoid hardcoding
        view.boxStrokeColor = Color.BLACK

        // See https://stackoverflow.com/questions/15543186/how-do-i-create-colorstatelist-programmatically
        val states = arrayOf(
            intArrayOf(R.attr.state_enabled),
            intArrayOf(-R.attr.state_enabled),
            intArrayOf(-R.attr.state_checked),
            intArrayOf(
                R.attr.state_pressed
            )
        )

        val colors = intArrayOf(
            Color.BLACK,
            Color.RED,
            Color.GREEN,
            Color.BLUE
        )

        view.hintTextColor = ColorStateList(states, colors)
    }
}
