package com.gani.lib.ui.view

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.media.MediaPlayer
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import com.gani.lib.ui.style.Length
import com.gani.lib.utils.Res

class GButton : AppCompatButton, IView {
    private var helper: ViewHelper = ViewHelper(this)

    private var customClickSound: MediaPlayer? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        this.helper = ViewHelper(this)
    }

    fun width(width: Int): GButton {
        helper.width(width)
        return self()
    }

    fun height(height: Int): GButton {
        helper.height(height)
        return self()
    }

    fun weight(weight: Float): GButton {
        helper.weight(weight)
        return self()
    }

    fun specs(vararg specs: Spec): GButton {
        for (spec in specs) {
            spec.decorate(this)
        }
        return self()
    }

    fun bgTint(color: Int): GButton {
        // Alternative implementation: http://stackoverflow.com/questions/1521640/standard-android-button-with-a-different-color
        background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        //    ViewCompat.setBackgroundTintList(this, ContextCompat.getColorStateList(getContext(), colorResId));
        return self()
    }

    fun bgColor(code: String): GButton {
        return bgColor(Res.color(code), 0)
    }

    @JvmOverloads
    fun bgColor(color: Int, cornerRadius: Int = 0): GButton {
        val drawable = GradientDrawable()
        drawable.setColor(color)
        drawable.cornerRadius = cornerRadius.toFloat()
        background = drawable
        return self()
    }

    // Can't be used before setting bgColor()
    fun borders(color: Int, thickness: Int, cornerRadius: Int): GButton {
        val borderColorDrawable = GradientDrawable()
        borderColorDrawable.cornerRadius = Length.dpToPx(cornerRadius).toFloat()
        borderColorDrawable.setStroke(Length.dpToPx(thickness), color)

        val backgroundColorDrawable = background
        val drawables = arrayOf(borderColorDrawable, backgroundColorDrawable)

        val layerDrawable = LayerDrawable(drawables)
        layerDrawable.setLayerInset(1, thickness, thickness, thickness, thickness)
        background = layerDrawable
        return self()
    }


    // Use bgColor() instead
    //  public T background(String code) {
    //    background(Ui.color(code));
    //    return self();
    //  }
    //
    // Use bgTint() instead
    //  public T background(int color) {
    //    // Alternative implementation: http://stackoverflow.com/questions/1521640/standard-android-button-with-a-different-color
    //     getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    ////    ViewCompat.setBackgroundTintList(this, ContextCompat.getColorStateList(getContext(), colorResId));
    //    return self();
    //  }

    //  public T background() {
    //
    //    return self();
    //  }

    fun color(code: String): GButton {
        color(Res.color(code))
        return self()
    }

    fun color(color: Int): GButton {
        setTextColor(color)
        return self()
    }

    fun text(text: String): GButton {
        setText(text)
        return self()
    }

    fun textSize(textSize: Float): GButton {
        setTextSize(textSize)
        return self()
    }

    fun bold(): GButton {
        return typeface(Typeface.DEFAULT_BOLD)
    }

    fun typeface(typeface: Typeface): GButton {
        setTypeface(typeface)
        return self()
    }

    fun gravity(alignment: Int): GButton {
        gravity = alignment
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GButton {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GButton {
        helper.margin(l, t, r, b)
        return self()
    }

    fun onClick(listener: (GButton) -> Unit) : GButton {
        onClick(OnClickListener { listener(this) })
        return self()
    }

    fun onClick(listener: View.OnClickListener): GButton {
        if (customClickSound == null) {
            helper.click(listener)
        } else {
            helper.click(OnClickListener { view ->
                customClickSound!!.start()
                listener.onClick(view)
            })
        }
        return self()
    }

    fun sound(customClickSound: MediaPlayer): GButton {
        this.customClickSound = customClickSound
        //        int maxVolume = 10;
        //        mp.setVolume();
        //        float log1=(float)(Math.log(maxVolume?-currVolume)/Math.log(maxVolume));
        //        mp.setVolume(1-log1);
        customClickSound.setVolume(1.0f, 1.0f)
        isSoundEffectsEnabled = false
        return self()
    }

    //  @Override
    //  public RelativeLayout.LayoutParams getLayoutParams() {
    //    return (RelativeLayout.LayoutParams) super.getLayoutParams();
    //  }

    private fun self(): GButton {
        return this
    }

//    fun alignParentRight(): T {
//        //    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
//        //    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        helper.alignParentRight()
//        return self()
//    }

    fun compoundDrawables(left: Drawable, top: Drawable, right: Drawable, bottom: Drawable): GButton {
        setCompoundDrawables(left, top, right, bottom)
        return self()
    }



    open class Spec(val decorator: (GButton) -> Unit) {
        fun decorate(view: GButton) {
            decorator(view)
        }
    }
}
