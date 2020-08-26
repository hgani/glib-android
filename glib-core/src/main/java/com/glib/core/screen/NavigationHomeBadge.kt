package com.glib.core.screen

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import com.glib.core.R
import com.glib.core.ui.style.Length
import com.glib.core.utils.Res

internal class NavigationHomeBadge(private val screen: GScreen) {
//    private val context: Context
    internal val drawable: LayerDrawable
    private var icon: Drawable? = null

    init {
//        this.context = screenView.layout.context
        this.drawable = Res.drawable(R.drawable.action_bar_home_drawable) as LayerDrawable

//        setCount(0)
    }

    fun setIcon(icon: Drawable?) {
        this.icon = icon
        setCount(0)
    }

    fun setCount(count: Int) {
        val badge: BadgeDrawable

//        val icon = screen.navMenuIcon()
        if (icon != null) {
//            val actionBarIcon = icon

            // Experiment with not reusing and see if it causes any issue
            badge = BadgeDrawable()
            badge.setCount(count)

            drawable.mutate()
            drawable.setDrawableByLayerId(R.id.ic_badge, badge)
            drawable.setDrawableByLayerId(R.id.ic_navigation, icon)
            drawable.invalidateSelf()
        }
    }


    private class BadgeDrawable() : Drawable() {
        private val mTextSize: Float
        private val mBadgePaint: Paint
        private val mTextPaint: Paint
        private val mTxtRect = Rect()

        private var mCount = ""
        private var mWillDraw = false

        init {
            mTextSize = Length.dpToPx(12).toFloat()

            mBadgePaint = Paint()
            mBadgePaint.color = Color.RED
            mBadgePaint.isAntiAlias = true
            mBadgePaint.style = Paint.Style.FILL

            mTextPaint = Paint()
            mTextPaint.color = Color.WHITE
            mTextPaint.typeface = Typeface.DEFAULT_BOLD
            mTextPaint.textSize = mTextSize
            mTextPaint.isAntiAlias = true
            mTextPaint.textAlign = Paint.Align.CENTER
        }

        override fun draw(canvas: Canvas) {
            if (!mWillDraw) {
                return
            }

            val bounds = bounds
            val width = (bounds.right - bounds.left).toFloat()
            val height = (bounds.bottom - bounds.top).toFloat()

            // Position the badge in the top-right quadrant of the icon.
            val temporaryRadius = (Math.min(width, height) / 2 - 1) / 2
            val centerX = width - temporaryRadius - 1f
            val centerY = temporaryRadius + 1

            val radius = mTextSize

            // Draw badge circle.
            canvas.drawCircle(centerX, centerY, radius, mBadgePaint)

            // Draw badge count text inside the circle.
            mTextPaint.getTextBounds(mCount, 0, mCount.length, mTxtRect)
            val textHeight = (mTxtRect.bottom - mTxtRect.top).toFloat()
            val textY = centerY + textHeight / 2f
            canvas.drawText(mCount, centerX, textY, mTextPaint)
        }

        internal fun setCount(count: Int) {
            mCount = Integer.toString(count)

            // Only draw a badge if there are notifications.
            mWillDraw = count > 0

            //      invalidateSelf();
        }

        override fun setAlpha(alpha: Int) {
            // do nothing
        }

        override fun setColorFilter(cf: ColorFilter?) {
            // do nothing
        }

        override fun getOpacity(): Int {
            return PixelFormat.UNKNOWN
        }
    }
}