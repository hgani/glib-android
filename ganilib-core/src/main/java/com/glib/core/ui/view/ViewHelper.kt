package com.glib.core.ui.view

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.glib.core.logging.GLog
import com.glib.core.ui.layout.GCoordinatorLayoutParams
import com.glib.core.ui.layout.GRelativeLayoutParams
import com.glib.core.ui.style.Length
import com.glib.core.utils.Res
import android.graphics.drawable.shapes.RoundRectShape







class ViewHelper @JvmOverloads constructor(private val view: View, layoutParams: ViewGroup.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)) {

    init {

        // Ensure layout params can't be null.
        view.layoutParams = layoutParams
    }

//    fun <T : View> relative(view: T): GRelativeLayoutParams<T> {
//        //    view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        val params = GRelativeLayoutParams(view)
//        this.view.layoutParams = params
//        return params
//    }

    fun relative(addRules: (GRelativeLayoutParams) -> (Unit)) {
        val params = (this.view.layoutParams as? GRelativeLayoutParams) ?: GRelativeLayoutParams()
        this.view.layoutParams = params
        addRules(params)
    }

    fun coordinator(addRules: (GCoordinatorLayoutParams) -> (Unit)) {
        val params = (this.view.layoutParams as? GCoordinatorLayoutParams) ?: GCoordinatorLayoutParams()
        this.view.layoutParams = params
        addRules(params)
    }

//    fun alignParentRight() {
//        val params = view.layoutParams as RelativeLayout.LayoutParams
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
//    }

    private fun size(width: Int?, height: Int?) {
        val params = view.layoutParams
        if (width != null) {
            if (width >= 0) {
                params.width = Length.dpToPx(width)
            } else {
                params.width = width
            }
        }
        if (height != null) {
            if (height >= 0) {
                params.height = Length.dpToPx(height)
            } else {
                params.height = height
            }
        }
        view.layoutParams = params
    }

    fun width(width: Int?) {
        size(width, null)
    }

    fun height(height: Int?) {
        size(null, height)
    }

    fun weight(weight: Float) {
        val params = view.layoutParams
        if (params is LinearLayout.LayoutParams) {
            params.weight = weight
        }
        view.layoutParams = params
    }

    fun padding(left: Int?, top: Int?, right: Int?, bottom: Int?) {
        var left = left
        var top = top
        var right = right
        var bottom = bottom
        if (left == null) {
            left = view.paddingLeft
        } else {
            left = Length.dpToPx(left)
        }

        if (top == null) {
            top = view.paddingTop
        } else {
            top = Length.dpToPx(top)
        }

        if (right == null) {
            right = view.paddingRight
        } else {
            right = Length.dpToPx(right)
        }

        if (bottom == null) {
            bottom = view.paddingBottom
        } else {
            bottom = Length.dpToPx(bottom)
        }

        view.setPadding(left, top, right, bottom)
    }

    fun margin(left: Int?, top: Int?, right: Int?, bottom: Int?) {
        var left = left
        var top = top
        var right = right
        var bottom = bottom
        val params = view.layoutParams
        if (params is ViewGroup.MarginLayoutParams) {
            if (left == null) {
                //        left = view.getPaddingLeft();
                left = params.leftMargin
            } else {
                left = Length.dpToPx(left)
            }

            if (top == null) {
                //        top = view.getPaddingTop();
                top = params.topMargin
            } else {
                top = Length.dpToPx(top)
            }

            if (right == null) {
                //        right = view.getPaddingRight();
                right = params.rightMargin
            } else {
                right = Length.dpToPx(right)
            }

            if (bottom == null) {
                //        bottom = view.getPaddingBottom();
                bottom = params.bottomMargin
            } else {
                bottom = Length.dpToPx(bottom)
            }

            params.setMargins(left, top, right, bottom)
        } else {
            GLog.w(javaClass, "Unable to put margin for: " + view)
        }
    }

    fun border(color: Int, width: Int) {
        val sd = ShapeDrawable()
        sd.shape = RectShape()
        sd.paint.color = color
        sd.paint.strokeWidth = Length.dpToPx(width).toFloat()
        sd.paint.style = Paint.Style.STROKE

        val drawables = mutableListOf<Drawable>()
        view.background?.let {
            drawables.add(it)
        }
        drawables.add(sd)

        view.background = LayerDrawable(drawables.toTypedArray())

//        // From: https://android--examples.blogspot.com/2016/10/android-textview-bottom-border.html
//        // Initialize new color drawables
//        val borderColorDrawable = ColorDrawable(borderColor)
//        val backgroundColorDrawable = ColorDrawable(Color.TRANSPARENT)
//
//        // Initialize a new array of drawable objects
//        val drawables = arrayOf<Drawable>(borderColorDrawable, backgroundColorDrawable)
//
//        // Initialize a new layer drawable instance from drawables array
//        val layerDrawable = LayerDrawable(drawables)
//
//        // Set padding for background color layer
//        layerDrawable.setLayerInset(
//                1, // Index of the drawable to adjust [background color layer]
//                left, // Number of pixels to add to the left bound [left border]
//                top, // Number of pixels to add to the top bound [top border]
//                right, // Number of pixels to add to the right bound [right border]
//                bottom // Number of pixels to add to the bottom bound [bottom border]
//        )
//
//        view.background = layerDrawable
    }


    fun roundedBorder(bgcolor: Int, borderColor: Int, radius: Float) {
        view.background = createRoundedBorders(bgcolor, borderColor, radius)
    }

    private fun createRoundedBorders(bgColor: Int, borderColor: Int, radius: Float): LayerDrawable {
        val m_arrfTopHalfOuterRadii = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        val m_arrfBottomHalfOuterRadii = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)

        val top_round_rect = RoundRectShape(m_arrfTopHalfOuterRadii, null, null)
        val top_shape_drawable = ShapeDrawable(top_round_rect)
        top_shape_drawable.paint.color = borderColor

        val bottom_round_rect = RoundRectShape(m_arrfBottomHalfOuterRadii, null, null)
        val bottom_shape_drawable = ShapeDrawable(bottom_round_rect)
        bottom_shape_drawable.paint.color = bgColor

        val drawarray = arrayOf<Drawable>(top_shape_drawable, bottom_shape_drawable)
        val layerdrawable = LayerDrawable(drawarray)

        layerdrawable.setLayerInset(0, 0, 0, 0, 0) //top half
        layerdrawable.setLayerInset(1, 4, 4, 4, 4) //bottom half

        return layerdrawable
    }


    //    fun createRoundedBorders(bgColor: Int, borderColor: Int): LayerDrawable {
//
//        val radius = 15.0f
//
//        val m_arrfTopHalfOuterRadii = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
//        val m_arrfBottomHalfOuterRadii = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
//
//        val top_round_rect = RoundRectShape(m_arrfTopHalfOuterRadii, null, null)
//        val top_shape_drawable = ShapeDrawable(top_round_rect)
//        top_shape_drawable.paint.color = borderColor
//
//        val bottom_round_rect = RoundRectShape(m_arrfBottomHalfOuterRadii, null, null)
//        val bottom_shape_drawable = ShapeDrawable(bottom_round_rect)
//        bottom_shape_drawable.paint.color = bgColor
//
//        val drawarray = arrayOf<Drawable>(top_shape_drawable, bottom_shape_drawable)
//        val layerdrawable = LayerDrawable(drawarray)
//
//        layerdrawable.setLayerInset(0, 0, 0, 0, 0) //top half
//        layerdrawable.setLayerInset(1, 4, 4, 4, 4) //bottom half
//
//        return layerdrawable
//    }

    fun click(listener: View.OnClickListener) {
        view.setOnClickListener(listener)
    }


    fun bgColor(color: Int) {
        view.setBackgroundColor(color)
    }

    fun bgColor(code: String) {
        view.setBackgroundColor(Res.color(code))
    }

    fun bg(res: Int) {
        view.background = Res.drawable(res)
    }

    fun bg(drawable: Drawable) {
        view.background = drawable
    }
}
