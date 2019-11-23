package com.glib.core.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View.OnClickListener
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.glib.core.utils.Res

class GImageView : AppCompatImageView, IView {
    private val helper = ViewHelper(this)
    private var circle: Boolean = false

    // Using application context prevents app crash when closing activity during image load
    private val glide = Glide.with(Res.context)

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

//    fun svgSource(url: String?): GImageView {
//        (context as? Activity)?.let {
//            val requestBuilder = GlideToVectorYou
//                    .init()
//                    .with(it)
//                    .requestBuilder
//
//            requestBuilder
//                    .load(url)
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .apply(RequestOptions().centerInside())
//                    .into(this)
////            GlideToVectorYou
////                    .init()
////                    .with(it)
////                    .apply(RequestOptions().centerCrop())
//////                    .withListener(new GlideToVectorYouListener() {
//////                        @Override
//////                        public void onLoadFailed() {
//////                            Toast.makeText(context, "Load failed", Toast.LENGTH_SHORT).show()
//////                        }
//////
//////                        @Override
//////                        public void onResourceReady() {
//////                            Toast.makeText(context, "Image ready", Toast.LENGTH_SHORT).show()
//////                        }
//////                    })
//////                    .setPlaceHolder(placeholderLoading, placeholderError)
////                    .load(Uri.parse(url), this);
//
////            GlideToVectorYou.justLoadImage(it, Uri.parse(url), this)
//        }
//        return this
//    }

    fun circle(): GImageView {
        circle = true
        return this
    }

    private fun glideIn(requestBuilder: RequestBuilder<Drawable>): GImageView {
        var chain = requestBuilder
        if (circle) {
            chain = requestBuilder.apply(RequestOptions().circleCrop())
        }
        chain.into(this)
        return this
    }

    fun source(url: String?): GImageView {
        return glideIn(glide.load(url))
    }

    fun source(drawable: Drawable): GImageView {
//        setImageDrawable(drawable)

        return glideIn(glide.load(drawable))
    }

    fun source(bitmap: Bitmap): GImageView {
//        setImageBitmap(bitmap)

        return glideIn(glide.load(bitmap))
    }

    fun border(color: Int, width: Int): GImageView {
        helper.border(color, width)
        return this
    }

    fun adjustBounds(): GImageView {
        adjustViewBounds = true
        return this
    }

    fun scaleType(type: ImageView.ScaleType): GImageView {
        setScaleType(type);
        return this
    }

    // Make sure to explicitly set both the view's width and height (either to a fixed value or MATCH_PARENT), otherwise the image will
    // get scaled up significantly because there is no upper limit.
    fun centerCrop(): GImageView {
        setScaleType(ImageView.ScaleType.CENTER_CROP)
        return this
    }

    fun onClick(listener: (GImageView) -> Unit): GImageView {
        helper.click(OnClickListener { listener(this) })
        return this
    }
}
