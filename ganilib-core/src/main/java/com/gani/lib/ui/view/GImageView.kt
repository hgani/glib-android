package com.gani.lib.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View.OnClickListener
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.gani.lib.utils.Res

class GImageView : AppCompatImageView, IView {
    private val helper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun width(width: Int?): GImageView {
        helper.width(width)
        return this
    }

    override fun height(height: Int?): GImageView {
        helper.height(height)
        return this
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GImageView {
        helper.padding(l, t, r, b)
        return this
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GImageView {
        helper.margin(l, t, r, b)
        return this
    }

    override fun bgColor(color: Int): GImageView {
        helper.bgColor(color)
        return this
    }

    fun bgColor(code: String): GImageView {
        setBackgroundColor(Res.color(code))
        return this
    }

    fun bg(res: Int): GImageView {
        helper.bg(res)
        return this
    }

    fun bg(drawable: Drawable): GImageView {
        helper.bg(drawable)
        return this
    }

    fun weight(weight: Float): GImageView {
        helper.weight(weight)
        return this
    }

    fun source(url: String?): GImageView {
        // If url is null, the image view will be blank, which is suitable behaviour for item holder reuse.
        Glide.with(Res.context)  // Using application context prevents app crash when closing activity during image load
                .load(url)
                .into(this)
        return this
    }

//    fun drawable(drawable: Drawable): GImageView {
//        setImageDrawable(drawable)
//        return this
//    }

    fun source(drawable: Drawable): GImageView {
        setImageDrawable(drawable)
        return this
    }

    fun source(bitmap: Bitmap): GImageView {
        setImageBitmap(bitmap)
        return this
    }

//    fun source(icon: GIcon): GImageView {
//        return source(drawable = icon.drawable())
//    }

//    fun drawable(resId: Int): GImageView {
//        setImageDrawable(Ui.drawable(resId))
//        return this
//    }

    fun border(color: Int, width: Int): GImageView {
        helper.border(color, width)
        return this
    }

    fun adjustBounds(): GImageView {
        adjustViewBounds = true
        return this
    }

    fun scaleType(type: ScaleType): GImageView {
        setScaleType(type);
        return this
    }

    // Make sure to explicitly set both the view's width and height (either to a fixed value or MATCH_PARENT), otherwise the image will
    // get scaled up significantly because there is no upper limit.
    fun centerCrop(): GImageView {
        setScaleType(ImageView.ScaleType.CENTER_CROP);
        return this
    }

    fun onClick(listener: (GImageView) -> Unit): GImageView {
        helper.click(OnClickListener { listener(this) })
        return this
    }
}
