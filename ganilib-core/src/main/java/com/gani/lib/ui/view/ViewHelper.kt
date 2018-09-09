package com.gani.lib.ui.view

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.gani.lib.logging.GLog
import com.gani.lib.ui.layout.GRelativeLayoutParams
import com.gani.lib.ui.style.Length
import com.gani.lib.utils.Res

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
        val params = GRelativeLayoutParams()
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

            //      ((ViewGroup.MarginLayoutParams) params).setMargins(
            //          Length.dpToPx(left),
            //          Length.dpToPx(top),
            //          Length.dpToPx(right),
            //          Length.dpToPx(bottom));


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
            //
            //      view.setPadding(left, top, right, bottom);

            params.setMargins(left, top, right, bottom)
        } else {
            GLog.w(javaClass, "Unable to put margin for: " + view)
        }
    }

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
