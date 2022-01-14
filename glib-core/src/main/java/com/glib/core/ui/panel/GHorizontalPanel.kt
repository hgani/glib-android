package com.glib.core.ui.panel

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.glib.core.ui.layout.GLinearLayout
import com.glib.core.ui.view.GButton
import com.glib.core.ui.view.IView
import java.lang.UnsupportedOperationException

class GHorizontalPanel: GLinearLayout<GHorizontalPanel>, IView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        horizontal()
    }



    open class Spec(val decorator: (GHorizontalPanel) -> Unit) {
//        companion object {
//            val LINK = GButton.Spec() { view ->
//                view.bgColor(Color.TRANSPARENT)
//                view.color(Color.parseColor("#1976d2"))
//            }
//
//            val ICON = GButton.Spec() { view ->
//                // TODO
//                throw UnsupportedOperationException("Not yet implemented")
//            }
//        }

        fun decorate(view: GHorizontalPanel) {
            decorator(view)
        }
    }
}
