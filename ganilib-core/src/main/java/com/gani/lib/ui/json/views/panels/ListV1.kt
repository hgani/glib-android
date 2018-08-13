package com.gani.lib.ui.json.views.panels

import android.view.View
import com.gani.lib.json.GJsonObject
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.list.GRecyclerView

class ListV1(spec: GJsonObject<*, *>, screen: GActivity): JsonView(spec, screen) {
    private val recyclerView = GRecyclerView(context)

    override fun initView(): View {
//        spec["subviews"].arrayValue.forEach { subviewSpec ->
//            JsonView.create(subviewSpec, screen)?.let {jsonView ->
//                panel.addView(jsonView.createView())
//            }
//        }
        return recyclerView
    }
}
