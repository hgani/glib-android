package com.gani.lib.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.layout.GCoordinatorLayoutParams

class GFloatingActionButton : FloatingActionButton {
    private val helper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

//    override fun width(width: Int?): GFloatingActionButton {
//        helper.width(width)
//        return self()
//    }
//
//    override fun height(height: Int?): GFloatingActionButton {
//        helper.height(height)
//        return self()
//    }
//
//    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GFloatingActionButton {
//        helper.padding(l, t, r, b)
////        this.minWidth = 0
////        this.minimumWidth = 0
////        this.minHeight = 0
////        this.minimumHeight = 0
//        return self()
//    }
//
//    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GFloatingActionButton {
//        helper.margin(l, t, r, b)
//        return self()
//    }
//
//    override fun bgColor(color: Int): GFloatingActionButton {
//        return bgColor(color, 0)
//    }
//
//    override fun weight(weight: Float): GFloatingActionButton {
//        helper.weight(weight)
//        return self()
//    }

//    fun specs(vararg specs: Spec): GFloatingActionButton {
//        for (spec in specs) {
//            spec.decorate(this)
//        }
//        return self()
//    }

//    fun bgTint(color: Int): GFloatingActionButton {
//        // Alternative implementation: http://stackoverflow.com/questions/1521640/standard-android-button-with-a-different-color
//        background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
//        //    ViewCompat.setBackgroundTintList(this, ContextCompat.getColorStateList(getContext(), colorResId));
//        return self()
//    }
//
//    fun bgColor(code: String): GFloatingActionButton {
//        return bgColor(Res.color(code), 0)
//    }
//
////    @JvmOverloads
//    fun bgColor(color: Int, cornerRadius: Int = 0): GFloatingActionButton {
//        val drawable = GradientDrawable()
//        drawable.setColor(color)
//        drawable.cornerRadius = cornerRadius.toFloat()
//        background = drawable
//        return self()
//    }

//    // Can't be used before setting bgColor()
//    fun borders(color: Int, thickness: Int, cornerRadius: Int): GFloatingActionButton {
//        val borderColorDrawable = GradientDrawable()
//        borderColorDrawable.cornerRadius = Length.dpToPx(cornerRadius).toFloat()
//        borderColorDrawable.setStroke(Length.dpToPx(thickness), color)
//
//        val backgroundColorDrawable = background
//        val drawables = arrayOf(borderColorDrawable, backgroundColorDrawable)
//
//        val layerDrawable = LayerDrawable(drawables)
//        layerDrawable.setLayerInset(1, thickness, thickness, thickness, thickness)
//        background = layerDrawable
//        return self()
//    }
//
//    fun color(code: String): GFloatingActionButton {
//        color(Res.color(code))
//        return self()
//    }
//
//    fun color(color: Int): GFloatingActionButton {
//        setTextColor(color)
//        return self()
//    }
//
//    fun text(text: String): GFloatingActionButton {
//        setText(text)
//        return self()
//    }
//
//    fun textSize(textSize: Float): GFloatingActionButton {
//        setTextSize(textSize)
//        return self()
//    }
//
//    fun bold(): GFloatingActionButton {
//        return typeface(Typeface.DEFAULT_BOLD)
//    }
//
//    fun typeface(typeface: Typeface): GFloatingActionButton {
//        setTypeface(typeface)
//        return self()
//    }
//
//    fun gravity(alignment: Int): GFloatingActionButton {
//        this.gravity = alignment
//        return self()
//    }

    fun source(drawable: Drawable?): GFloatingActionButton {
        setImageDrawable(drawable)
        return self()
    }

    fun onClick(listener: (GFloatingActionButton) -> Unit): GFloatingActionButton {
        onClick(OnClickListener { listener(this) })
        return self()
    }

    fun onClick(listener: View.OnClickListener): GFloatingActionButton {
        helper.click(listener)
        return self()
    }

    private fun self(): GFloatingActionButton {
        return this
    }

    fun coordinator(addRules: (GCoordinatorLayoutParams) -> (Unit)): GFloatingActionButton {
        helper.coordinator(addRules)
        return self()
    }

    fun attachTo(fragment: GFragment): GFloatingActionButton {
        fragment.coordinator.addView(this)
        return self()
    }



//    open class Spec(val decorator: (GFloatingActionButton) -> Unit) {
//        fun decorate(view: GFloatingActionButton) {
//            decorator(view)
//        }
//    }
}
