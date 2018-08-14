package com.gani.lib.ui.view

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.View
import com.gani.lib.utils.Res
import java.util.*
import java.util.regex.Pattern

open class GTextView : AppCompatTextView {
    private var helper: ViewHelper = ViewHelper(this)

    constructor(context: Context) : super(context) {
//        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        init()
    }

//    private fun init() {
//        this.helper = ViewHelper(this)
//    }

    private fun self(): GTextView {
        return this
    }

//    fun relative(): GRelativeLayoutParams<T> {
//        return helper.relative(self())
//    }

    fun spec(spec: Spec): GTextView {
        spec.decorate(this)
        return this
    }

    fun width(width: Int?): GTextView {
        helper.width(width)
        return self()
    }

    fun height(height: Int?): GTextView {
        helper.height(height)
        return self()
    }

    fun processBold(): GTextView {
        val matchers = ArrayList<String>()
        val text = text.toString()
        val builder = SpannableStringBuilder(text)
        val boldPattern = Pattern.compile("\\*([A-z0-9 ]+)\\*")
        val matcher = boldPattern.matcher(builder)

        while (matcher.find()) {
            matchers.add(matcher.group())
        }

        for (i in matchers.indices.reversed()) {
            val m = matchers[i]
            val startIndex = text.indexOf(m)
            val endIndex = startIndex + m.length

            val str = SpannableString(m.substring(1, m.length - 1))
            str.setSpan(StyleSpan(Typeface.BOLD), 0, str.length, 0)
            builder.replace(startIndex, endIndex, str)
        }

        setText(builder)

        return this
    }

    fun bgColor(code: String): GTextView {
        bgColor(Res.color(code))
        return self()
    }

    fun bgColor(color: Int): GTextView {
        setBackgroundColor(color)
        return self()
    }

    fun color(code: String): GTextView {
        return color(Res.color(code))
    }

    fun color(color: Int): GTextView {
        setTextColor(color)
        return self()
    }

    fun paddings(l: Int?, t: Int?, r: Int?, b: Int?): GTextView {
        helper.padding(l, t, r, b)
        return self()
    }

    fun margins(l: Int?, t: Int?, r: Int?, b: Int?): GTextView {
        helper.margin(l, t, r, b)
        return self()
    }

    fun bold(): GTextView {
        return typeface(Typeface.DEFAULT_BOLD)
    }

    fun typeface(typeface: Typeface): GTextView {
        setTypeface(typeface)
        return this
    }

    fun text(text: String): GTextView {
        setText(text)
        return this
    }

    fun textSize(textSize: Float): GTextView {
        setTextSize(textSize)
        //    setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        return this
    }

    //  public GTextView textSize(int unit, float textSize) {
    //    setTextSize(unit, textSize);
    //    return this;
    //  }

    fun gravity(alignment: Int): GTextView {
        gravity = alignment
        return this
    }

    fun onClick(listener: View.OnClickListener): GTextView {
        helper.click(listener)
        return this
    }

    // NOTE: Has to be called before setting the text.
    // See https://stackoverflow.com/questions/27927930/android-linkify-clickable-telephone-numbers
    fun linkify(mask: Int): GTextView {
        autoLinkMask = mask
        linksClickable = true
        return this
    }

    fun ellipsize(where: TextUtils.TruncateAt): GTextView {
        ellipsize = where
        return this
    }

    fun maxLines(max: Int): GTextView {
        maxLines = max
        return this
    }


    //  EnumSet<FileAccess> readWrite = EnumSet.of(FileAccess.Read, FileAccess.Write);


    open class Spec {
        companion object {
            val cellTitle = GTextView.Spec() { view ->
                view.textSize(18f).bold()
            }

            val cellSubtitle = GTextView.Spec() { view ->
                view.textSize(16f)
            }
        }

        private val decorator: (GTextView) -> Unit

        constructor(decorator: (GTextView) -> Unit ) {
            this.decorator = decorator
        }

        fun decorate(view: GTextView) {
            decorator(view)
        }
    }
}
