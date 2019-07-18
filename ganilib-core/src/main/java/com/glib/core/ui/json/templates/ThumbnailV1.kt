package com.glib.core.ui.json.templates

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonTemplate
import com.glib.core.ui.json.views.panels.TemplateHolder
import com.glib.core.ui.list.templates.ThumbnailHolder

class ThumbnailV1(spec: GJson, screen: GActivity): JsonTemplate(spec, screen) {
    companion object {
        private val VIEW_TYPE = View.generateViewId()
    }

    override val viewType: Int
        get() = VIEW_TYPE

    override fun createHolder(): TemplateHolder {
        return object : ThumbnailHolder<JsonTemplate>(context, true) {
            override fun update(item: JsonTemplate) {
                val spec = item.spec
                container.onClick(View.OnClickListener {
                    JsonAction.execute(spec["onClick"], item.screen, null, null)
                })
                setImageUrl(spec["imageUrl"].string)
                setTitle(spec["title"].string)
                setSubtitle(spec["subtitle"].string)
            }
        }
    }
}
