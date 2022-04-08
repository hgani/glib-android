package com.glib.core.ui.json.views.panels

import android.view.Gravity
import android.view.View
import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.icon.GIcon
import com.glib.core.ui.json.JsonUi
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.panel.GHorizontalPanel
import com.glib.core.ui.panel.GSplitPanel
import com.glib.core.ui.panel.GVerticalPanel
import com.glib.core.ui.view.GImageView
import com.glib.core.ui.view.GView
import java.lang.RuntimeException

class Ul(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val panel = GVerticalPanel(context)

    override fun initView(): View {
        val childViews = spec["childViews"].arrayValue.mapNotNull { subviewSpec ->
            create(subviewSpec, screen, fragment)?.createView()
        }

        childViews.forEach { childView ->
            val horizontal = GHorizontalPanel(context)
                .gravity(Gravity.CENTER_VERTICAL)
                .padding(null, 4, null, null)

            JsonUi.mdIconDrawable("chevron_right", 12)?.let { iconDrawable ->
                val bullet = GImageView(context)
                    .source(iconDrawable)
                    .margin(null, null, 4, null)
                panel.addView(horizontal.append(bullet).append(childView))
            }
        }

        return panel
    }
//
//    private fun createSubview(subviewSpec: GJson) : View {
//        if (subviewSpec.isNull()) {
//            return GView(context).width(0)
//        }
//
////        return create(subviewSpec, screen, fragment)?.createView() ?: GView(context)
//        return Default(subviewSpec, screen, fragment).createView()
//    }
}
