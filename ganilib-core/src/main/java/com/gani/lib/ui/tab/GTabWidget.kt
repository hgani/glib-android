package com.gani.lib.ui.tab

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TabWidget
import com.gani.lib.ui.view.ViewHelper
import com.gani.lib.utils.Res

open class GTabWidget : TabWidget {
    private val helper: ViewHelper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private fun self(): GTabWidget {
        return this
    }

    fun width(width: Int?): GTabWidget {
        helper.width(width)
        return self()
    }

    fun height(height: Int?): GTabWidget {
        helper.height(height)
        return self()
    }

    fun bgColor(code: String): GTabWidget {
        bgColor(Res.color(code))
        return self()
    }

    fun bgColor(color: Int): GTabWidget {
        setBackgroundColor(color)
        return self()
    }

    fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GTabWidget {
        helper.padding(l, t, r, b)
        return self()
    }

    fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GTabWidget {
        helper.margin(l, t, r, b)
        return self()
    }

    fun id(id: Int): GTabWidget {
        this.id = id
        return self()
    }

    fun append(child: View): GTabWidget {
        addView(child)
        return self()
    }


//    fun onClick(listener: View.OnClickListener): GTabHost {
//        helper.click(listener)
//        return this
//    }
}
