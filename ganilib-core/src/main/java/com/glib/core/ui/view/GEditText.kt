package com.glib.core.ui.view

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.TextView
import com.glib.core.R
import com.glib.core.logging.GLog
import com.glib.core.utils.Res
import com.google.android.material.textfield.TextInputEditText


class GEditText : TextInputEditText, IView {
    private val helper = ViewHelper(this)

    private var onTextChanged: ((GEditText) -> Unit)? = null
    private var textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            // Do nothing
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // Do nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChanged?.invoke(this@GEditText)
        }
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        // See https://stackoverflow.com/questions/21397977/android-edit-text-cursor-is-not-visible
        // See https://stackoverflow.com/questions/11554078/set-textcursordrawable-programmatically
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            val f = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            f.isAccessible = true
            f.set(this, R.drawable.edit_text_cursor)
        } catch (e: Exception) {
            GLog.e(javaClass, "Failed setting cursor", e)
        }

        addTextChangedListener(textWatcher)
    }

    // From https://stackoverflow.com/questions/6355096/how-to-create-edittext-with-crossx-button-at-end-of-it
    fun resetable(): GEditText {
        val originalText = this.text.toString()

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                val clearIcon = if (editable?.toString() != originalText) R.drawable.abc_ic_clear_material else 0
                setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })

        setOnTouchListener(View.OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                    this.setText(originalText)
                    return@OnTouchListener true
                }
            }
            return@OnTouchListener false
        })
        return this
    }

    private fun self(): GEditText {
        return this
    }

    override fun width(width: Int?): GEditText {
        helper.width(width)
        return self()
    }

    override fun height(height: Int?): GEditText {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GEditText {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GEditText {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun bgColor(color: Int): GEditText {
        setBackgroundColor(color)
        return self()
    }

    fun bgColor(code: String): GEditText {
        return bgColor(Res.color(code))
    }

    fun color(code: String): GEditText {
        return color(Res.color(code))
    }

    fun color(color: Int): GEditText {
        setTextColor(color)
        return self()
    }

    fun onTextChanged(listener: (GEditText) -> Unit): GEditText {
        this.onTextChanged = listener
        return self()
    }

    fun maxLines(lines: Int): GEditText {
        this.maxLines = lines
        return self()
    }

    // See https://stackoverflow.com/questions/41594657/android-bug-with-edittext-maxlines/41595500
    fun singleLine(): GEditText {
        this.maxLines = 1
        this.inputType = TYPE_CLASS_TEXT

        return self()
    }

    fun hint(hint: String): GEditText {
        this.hint = hint
        return self()
    }

    fun inputType(type: Int): GEditText {
        this.inputType = type
        return self()
    }

    fun typeface(typeface: Typeface): GEditText {
        setTypeface(typeface)
        return this
    }

    fun text(text: String): GEditText {
        setText(text);
        return this
    }

    fun enabled(enabled: Boolean): GEditText {
        isEnabled = enabled
        return this
    }

    fun onFocus(listener: (GEditText, Boolean) -> Unit) : GEditText {
        onFocus(OnFocusChangeListener { _, hasFocus -> listener(this, hasFocus) })
        return this
    }

    fun onFocus(listener: View.OnFocusChangeListener): GEditText {
        setOnFocusChangeListener(listener)
        return this
    }


}
