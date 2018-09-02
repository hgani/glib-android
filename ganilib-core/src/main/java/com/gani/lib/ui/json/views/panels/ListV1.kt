package com.gani.lib.ui.json.views.panels

import android.view.View
import android.view.ViewGroup
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonTemplate
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.list.DtoBindingHolder
import com.gani.lib.ui.list.DtoRecyclerAdapter
import com.gani.lib.ui.list.GRecyclerView

typealias TemplateHolder = DtoBindingHolder<JsonTemplate>

class ListV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val recyclerView = GRecyclerView(context).separator(true)
    private val templateRegistry = mutableMapOf<Int, JsonTemplate>()

    override fun initView(): View {
        val templates = mutableListOf<JsonTemplate>()

        spec["sections"].arrayValue.forEach { sectionSpec ->
            sectionSpec["rows"].arrayValue.forEach { rowSpec ->
                JsonTemplate.create(rowSpec, screen)?.let {
                    templates.add(it)

                    if (!templateRegistry.containsKey(it.viewType)) {
                        templateRegistry.put(it.viewType, it)
                    }
                }
            }
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
        }
    }
}
