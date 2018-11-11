package com.gani.lib.ui.json.templates

import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.JsonAction
import com.gani.lib.ui.json.JsonTemplate
import com.gani.lib.ui.json.views.panels.TemplateHolder
import com.gani.lib.ui.list.templates.FeaturedHolder

class FeaturedV1(spec: GJson, screen: GActivity): JsonTemplate(spec, screen) {
    companion object {
        private val VIEW_TYPE = View.generateViewId()
    }

    override val viewType: Int
        get() = VIEW_TYPE

    override fun createHolder(): TemplateHolder {
        return object : FeaturedHolder<JsonTemplate>(context, true) {
            override fun update(item: JsonTemplate) {
                val spec = item.spec
                container.onClick(View.OnClickListener {
                    JsonAction.execute(spec["onClick"], item.screen, null)
                })
                image.source(url = spec["imageUrl"].stringValue)
                title.text(spec["title"].stringValue)
                subtitle.text(spec["subtitle"].stringValue)
            }
        }
    }
}
