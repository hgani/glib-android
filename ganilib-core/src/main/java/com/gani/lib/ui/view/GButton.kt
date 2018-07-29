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
import com.gani.lib.ui.layout.GRelativeLayoutParams
import com.gani.lib.ui.style.Length
import com.gani.lib.utils.Res

class GButton<T : GButton<T>> : AppCompatButton {
    private var helper: ViewHelper = ViewHelper(this)

    private var customClickSound: MediaPlayer? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    fun relative(): GRelativeLayoutParams<T> {
        return helper.relative(self())
    }

    private fun init() {
        this.helper = ViewHelper(this)
    }

    fun width(width: Int?): T {
        helper.width(width)
        return self()
    }

    fun height(height: Int?): T {
        helper.height(height)
        return self()
    }

    fun spec(spec: Spec): T {
        spec.init(this)
        return self()
    }

    fun bgTint(color: Int): T {
        // Alternative implementation: http://stackoverflow.com/questions/1521640/standard-android-button-with-a-different-color
        background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        //    ViewCompat.setBackgroundTintList(this, ContextCompat.getColorStateList(getContext(), colorResId));
        return self()
    }

    fun bgColor(code: String): T {
        return bgColor(Res.color(code), 0)
    }

    @JvmOverloads
    fun bgColor(color: Int, cornerRadius: Int = 0): T {
        val drawable = GradientDrawable()
        drawable.setColor(color)
        drawable.cornerRadius = cornerRadius.toFloat()
        background = drawable
        return self()
    }

    // Can't be used before setting bgColor()
    fun borders(color: Int, thickness: Int, cornerRadius: Int): T {
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

    fun color(code: String): T {
        color(Res.color(code))
        return self()
    }

    fun color(color: Int): T {
        setTextColor(color)
        return self()
    }

    fun text(text: String): T {
        setText(text)
        return self()
    }

    fun textSize(textSize: Float): T {
        setTextSize(textSize)
        return self()
    }

    fun bold(): T {
        return typeface(Typeface.DEFAULT_BOLD)
    }

    fun typeface(typeface: Typeface): T {
        setTypeface(typeface)
        return self()
    }

    fun gravity(alignment: Int): T {
        gravity = alignment
        return self()
    }

//    override fun paddings(left: Int?, top: Int?, right: Int?, bottom: Int?): T {
//        helper.padding(left, top, right, bottom)
//        return self()
//    }
//
//    override fun margins(left: Int?, top: Int?, right: Int?, bottom: Int?): T {
//        helper.margin(left, top, right, bottom)
//        return self()
//    }
//
//    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): T {
//        helper.padding(l, t, r, b)
//        return self()
//    }
//
//    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): T {
//        helper.margin(l, t, r, b)
//        return self()
//    }

    fun paddings(l: Int?, t: Int?, r: Int?, b: Int?): T {
        helper.padding(l, t, r, b)
        return self()
    }

    fun margins(l: Int?, t: Int?, r: Int?, b: Int?): T {
        helper.margin(l, t, r, b)
        return self()
    }



    fun onClick(listener: View.OnClickListener): T {
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

    fun sound(customClickSound: MediaPlayer): T {
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

    private fun self(): T {
        return this as T
    }

    fun alignParentRight(): T {
        //    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        //    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        helper.alignParentRight()
        return self()
    }

    fun compoundDrawables(left: Drawable, top: Drawable, right: Drawable, bottom: Drawable): T {
        setCompoundDrawables(left, top, right, bottom)
        return self()
    }


    interface Spec {
        fun init(button: GButton<*>)
    }
}
