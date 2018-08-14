package com.gani.lib.ui.json.views.panels

import android.view.View
import android.view.ViewGroup
import com.gani.lib.json.GJson
import com.gani.lib.logging.GLog
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.JsonTemplate
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.list.DtoBindingHolder
import com.gani.lib.ui.list.DtoRecyclerAdapter
import com.gani.lib.ui.list.GRecyclerView

typealias TemplateHolder = DtoBindingHolder<JsonTemplate>

class ListV1(spec: GJson, screen: GActivity): JsonView(spec, screen) {
    private val recyclerView = GRecyclerView(context)
    private val templates = mutableListOf<JsonTemplate>()
    private val templateRegistry = mutableMapOf<Int, JsonTemplate>()

    override fun initView(): View {
        GLog.t(javaClass, "initView1 $spec")
        spec["sections"].arrayValue.forEach { sectionSpec ->
            GLog.t(javaClass, "initView2")
            sectionSpec["rows"].arrayValue.forEach { rowSpec ->
                GLog.t(javaClass, "initView3")
                JsonTemplate.create(rowSpec, screen)?.let {
                    templates.add(it)

                    if (!templateRegistry.containsKey(it.viewType)) {
                        templateRegistry.put(it.viewType, it)
                    }
                }


            }

            //            JsonView.create(subviewSpec, screen)?.let {jsonView ->
//                panel.addView(jsonView.createView())
//            }
        }
        recyclerView.adapter(ListAdapter(templates))
        return recyclerView
    }



    inner class ListAdapter(objects: List<JsonTemplate>) : DtoRecyclerAdapter<JsonTemplate, DtoBindingHolder<JsonTemplate>>(objects) {
        override fun onCreateItemHolder(parent: ViewGroup, viewType: Int): DtoBindingHolder<JsonTemplate> {
            return templateRegistry[viewType]!!.createHolder()
        }

        override fun determineViewType(item: JsonTemplate): Int {
            return item.viewType
//            item.initHolder()
//            item.spec["template"]
//            return super.determineViewType(item)
        }

//        override fun determineViewType(item: GJson): Int {
//            return super.determineViewType(item)
//        }
    }
//
//    class EventHolder(context: Context, selectable: Boolean) : DtoBindingHolder<Event>(context, selectable) {
//        private val container = GLinearLayout(context).vertical()
//        private val image = GImageView(context).height(200).centerCrop()
//        private val name = GTextView(context).spec(MyTextViewSpec.h1)
//        private val description = GTextView(context).spec(MyTextViewSpec.p).maxLines(1).ellipsize(TextUtils.TruncateAt.END)
//
//        init {
//            layout.addView(container.paddings(10, 10, 10, 10)
//                    .append(image)
//                    .append(GLinearLayout(context).vertical().paddings(10, 5, 10, 10).append(name).append(description)))
//        }
//
//        override fun update(item: Event) {
//            container.onClick(View.OnClickListener {
//                context.startActivity(EventDetailScreen.intent(item))
//            })
//            image.source(url = item.imageUrl)
//            name.text(item.name)
//            description.text(item.description)
//        }
//    }
}
