package com.glib.core.ui.pager

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.glib.core.ui.view.GImageView
import com.glib.core.utils.Res
import java.util.*

class DrawablePagerAdapter : PagerAdapter() {
    private var drawables = ArrayList<Drawable>()
    private var urls: Array<String>? = null
    private var height: Int? = null

    fun height(height: Int?): DrawablePagerAdapter {
        this.height = height
        return this
    }

    fun clear(): DrawablePagerAdapter {
//        this.drawables = null
        drawables.clear()
        this.urls = null
        notifyDataSetChanged()
        return this
    }

    fun drawables(vararg resources: Int): DrawablePagerAdapter {
//        drawables = ArrayList(resources.size)
        drawables.clear()
        for (res in resources) {
            Res.drawable(res)?.let {
                drawables.add(it)
            }
        }
        notifyDataSetChanged()
        return this
    }

    fun urls(vararg urls: String): DrawablePagerAdapter {
        this.urls = urls.asList().toTypedArray()
        notifyDataSetChanged()
        return this
    }

    fun urls(urls: List<String>): DrawablePagerAdapter {
        return urls(*urls.toTypedArray())
    }

    // See https://stackoverflow.com/questions/7263291/viewpager-pageradapter-not-updating-the-view
    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
//        if (drawables != null) {
        if (drawables.size > 0) {
            return drawables.size
        } else if (urls != null) {
            return urls!!.size
        }
        return 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = GImageView(container.context).height(height).centerCrop()
//                .adjustBounds()

//        if (drawables != null) {
        if (drawables.size > 0) {
            view.source(drawables[position])
        } else if (urls != null) {
            view.source(urls!![position])
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}