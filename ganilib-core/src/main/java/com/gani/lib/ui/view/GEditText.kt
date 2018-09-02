package com.gani.lib.ui.view

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.TextView
import com.gani.lib.R
import com.gani.lib.logging.GLog
import com.gani.lib.utils.Res


class GEditText : AppCompatEditText, IView {
    private var helper = ViewHelper(this)

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

    private fun self() : GEditText {
        return this
    }

    fun bgColor(code: String): GEditText {
        bgColor(Res.color(code))
        return self()
    }

    fun bgColor(color: Int): GEditText {
        setBackgroundColor(color)
        return self()
    }

    fun width(width: Int?) : GEditText {
        helper.width(width)
        return self()
    }

    fun height(height: Int?) : GEditText {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?) : GEditText {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?) : GEditText {
        helper.margin(l, t, r, b)
        return self()
    }

    fun onTextChanged(listener: (GEditText) -> Unit) : GEditText {
        this.onTextChanged = listener
        return self()
    }

    fun maxLines(lines: Int) : GEditText {
        this.maxLines = lines
        return self()
    }
}
