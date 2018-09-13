package com.gani.lib.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.gani.lib.utils.Res

class GImageView : AppCompatImageView, IView {
    private var helper: ViewHelper = ViewHelper(this)

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

    override fun bgColor(res: Int): GImageView {
        helper.bgColor(res)
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

    fun source(url: String?): GImageView {
        if (url != null) {
//            Glide.with(context.applicationContext)  // Using application context prevents app crash when closing activity during image load
//                    .load(url)
//                    .into(this)
            Glide.with(Res.context)  // Using application context prevents app crash when closing activity during image load
                    .load(url)
                    .into(this)
        }
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

    fun adjustBounds(): GImageView {
        adjustViewBounds = true
        return this
    }

    fun scaleType(type: ScaleType): GImageView {
        setScaleType(type);
        return this
    }

    fun centerCrop(): GImageView {
        setScaleType(ImageView.ScaleType.CENTER_CROP);
        return this
    }

    fun onClick(listener: View.OnClickListener): GImageView {
        helper.click(listener)
        return this
    }
}
