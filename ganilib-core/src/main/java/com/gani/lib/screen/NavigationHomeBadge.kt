package com.gani.lib.screen

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import com.gani.lib.R
import com.gani.lib.ui.style.Length
import com.gani.lib.utils.Res

internal class NavigationHomeBadge(private val screenView: NavHelper) {
    private val context: Context
    internal val drawable: LayerDrawable

//    internal val drawable: LayerDrawable

    init {
        this.context = screenView.layout.context
        this.drawable = Res.drawable(R.drawable.action_bar_home_drawable) as LayerDrawable

//        // Gets the drawable layerlist that has the navigation view and the badger
//        drawable = (if (Build.VERSION.SDK_INT >= 21)
//            context.applicationContext.getDrawable(R.drawable.action_bar_home_drawable)
//        else
//            context.resources.getDrawable(R.drawable.action_bar_home_drawable)) as LayerDrawable

        setCount(0)
    }

//    fun getDrawable(): Drawable {
//        return drawable
//    }

    fun setCount(count: Int) {
        val badge: BadgeDrawable

        val icon = screenView.menuIcon()
        if (icon != null) {
            val actionBarIcon = icon

            // Experiment with not reusing and see if it causes any issue
            badge = BadgeDrawable(context)
            badge.setCount(count)

            drawable.mutate()
            drawable.setDrawableByLayerId(R.id.ic_badge, badge)
            drawable.setDrawableByLayerId(R.id.ic_navigation, actionBarIcon)
            drawable.invalidateSelf()
        }
    }


    private class BadgeDrawable(context: Context) : Drawable() {

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

        //    static void setBadgeCount(Context context, LayerDrawable icon, int count) {
        //      BadgeDrawable badge;
        ////      Drawable actionBarIcon = new IconDrawable(context, CvIcon.cv_menu).actionBarSize();
        ////      Drawable actionBarIcon = CvIcon.cv_menu.drawable().actionBarSize();
        //      Drawable actionBarIcon = screenView.menuIcon();
        //
        //      // Reuse drawable if possible
        ////      Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        ////      if (reuse != null && reuse instanceof BadgeDrawable) {
        ////        badge = (BadgeDrawable) reuse;
        ////      } else {
        ////        badge = new BadgeDrawable(context);
        ////      }
        //
        //      // Experiment with not reusing and see if it causes any issue
        //      badge = new BadgeDrawable(context);
        //      badge.setCount(count);
        //
        //      icon.mutate();
        //      icon.setDrawableByLayerId(R.id.ic_badge, badge);
        //      icon.setDrawableByLayerId(R.id.ic_navigation, actionBarIcon);
        //      icon.invalidateSelf();
        //    }
    }
}