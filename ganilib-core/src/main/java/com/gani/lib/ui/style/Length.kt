package com.gani.lib.ui.style

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

import com.gani.lib.utils.Res

object Length {
    fun pxToDp(px: Int): Int {
        return Math.round(px / Res.resources.displayMetrics.density)
    }

    fun dpToPx(dp: Int): Int {
        return Math.round(dp * Res.resources.displayMetrics.density)
    }

    private fun windowRawSize(): Point {
        val wm = Res.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        wm.defaultDisplay.getSize(size)
        return size
        //    Display display = wm.getDefaultDisplay();
        //    return new Point(display.getWidth(), display.getHeight());
    }

    fun longerSide(): Int {
        val size = windowRawSize()
        return if (size.x > size.y) size.x else size.y
    }

    fun windowWidth(): Int {
        return pxToDp(windowRawSize().x)
    }

    fun windowWidthPx(): Int {
        return windowRawSize().x
    }

    fun windowHeight(): Int {
        return pxToDp(windowRawSize().y)
    }

    fun windowHeightPx(): Int {
        return windowRawSize().y
    }
}
