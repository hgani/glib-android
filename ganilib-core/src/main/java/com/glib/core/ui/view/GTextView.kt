package com.glib.core.ui.view

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatTextView
import com.glib.core.utils.Res
import java.util.*
import java.util.regex.Pattern



open class GTextView : AppCompatTextView, IView {
    private val helper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private fun self(): GTextView {
        return this
    }

    fun specs(vararg specs: Spec): GTextView {
        for (spec in specs) {
            spec.decorate(this)
        }
        return self()
    }

    override fun width(width: Int?): GTextView {
        helper.width(width)
        return self()
    }

    override fun height(height: Int?): GTextView {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GTextView {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GTextView {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun bgColor(color: Int): GTextView {
        helper.bgColor(color)
        return self()
    }

    fun bgColor(code: String): GTextView {
        bgColor(Res.color(code))
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

    fun underline(): GTextView {
        val text = text.toString()
        val content = SpannableString(text)
        content.setSpan(UnderlineSpan(), 0, text.length, 0)
        setText(content)

        return this
    }

    fun color(code: String): GTextView {
        return color(Res.color(code))
    }

    fun color(color: Int): GTextView {
        setTextColor(color)
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

    fun lineSpacing(add: Float, multiplier: Float? = null): GTextView {
        var m = this.lineSpacingMultiplier
        multiplier?.let { m = multiplier }
        setLineSpacing(add, m)
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

    fun onClick(listener: (GTextView) -> Unit) : GTextView {
        onClick(OnClickListener { listener(this) })
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


    open class Spec(val decorator: (GTextView) -> Unit) {
        companion object {
            val cellTitle = GTextView.Spec() { view ->
                view.textSize(18f).bold()
            }

            val cellSubtitle = GTextView.Spec() { view ->
                view.textSize(16f)
            }
        }

        fun decorate(view: GTextView) {
            decorator(view)
        }
    }
}
