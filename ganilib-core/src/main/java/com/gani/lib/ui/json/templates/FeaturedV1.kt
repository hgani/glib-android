package com.gani.lib.ui.json.templates

import android.content.Context
import android.text.TextUtils
import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.JsonAction
import com.gani.lib.ui.json.JsonTemplate
import com.gani.lib.ui.json.views.panels.TemplateHolder
import com.gani.lib.ui.layout.GLinearLayout
import com.gani.lib.ui.view.GAbstractTextView
import com.gani.lib.ui.view.GImageView

class FeaturedV1(spec: GJson, screen: GActivity): JsonTemplate(spec, screen) {
    companion object {
        private val VIEW_TYPE = View.generateViewId()
    }

    override val viewType: Int
        get() = VIEW_TYPE

    override fun createHolder(): TemplateHolder {
        return Holder(context, true)
    }

    class Holder(context: Context, selectable: Boolean) : TemplateHolder(context, selectable) {
        private val container = GLinearLayout(context).vertical()
        private val image = GImageView(context).height(200).centerCrop()
        private val name = GAbstractTextView(context).spec(GAbstractTextView.Spec.cellTitle)
        private val description = GAbstractTextView(context).spec(GAbstractTextView.Spec.cellSubtitle).maxLines(1).ellipsize(TextUtils.TruncateAt.END)

        init {
            layout.addView(container.paddings(10, 10, 10, 10)
                    .append(image)
                    .append(GLinearLayout(context).vertical().paddings(10, 5, 10, 10).append(name).append(description)))
        }

        override fun update(item: JsonTemplate) {
            val spec = item.spec
            container.onClick(View.OnClickListener {
                JsonAction.executeAll(spec["onClick"], item.screen)
            })
            image.source(url = spec["imageUrl"].stringValue)
            name.text(spec["title"].stringValue)
            description.text(spec["subtitle"].stringValue)
        }
    }
}
