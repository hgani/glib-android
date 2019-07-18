package com.gani.lib.ui.json.views.panels

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gani.lib.http.GRestCallback
import com.gani.lib.http.HttpAsync
import com.gani.lib.http.Rest
import com.gani.lib.json.GJson
import com.gani.lib.json.GJsonObject
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.ProgressIndicator
import com.gani.lib.ui.json.JsonTemplate
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.json.templates.ThumbnailV1
import com.gani.lib.ui.list.DtoBindingHolder
import com.gani.lib.ui.list.DtoRecyclerAdapter
import com.gani.lib.ui.list.GRecyclerView



typealias TemplateHolder = DtoBindingHolder<JsonTemplate>

class ListV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val recyclerView = GRecyclerView(context).separator(true)
    private val templateRegistry = mutableMapOf<Int, JsonTemplate>()

    private var nextUrl: String? = null
    private var autoLoad: Boolean = false
    private var request: HttpAsync? = null
    private val templates = mutableListOf<JsonTemplate>()
    private val adapter = ListAdapter(templates)
    private var loadMoreTemplate: JsonTemplate? = null

    override fun initView(): View {
        recyclerView.adapter(adapter)

        initNextPageInstructions(spec)
        appendItems(spec)
        appendLoadMoreIndicator()
        detectScrollBottom()

        return recyclerView
    }

    private fun initNextPageInstructions(spec: GJson) {
        autoLoad = false
        spec["nextPage"].presence?.let {
            nextUrl = it["url"].string
            autoLoad = it["autoLoad"].boolValue
        }
    }

    private fun appendItems(spec: GJson) {
        spec["sections"].arrayValue.forEach { sectionSpec ->
            sectionSpec["rows"].arrayValue.forEach { rowSpec ->
                JsonTemplate.create(rowSpec, screen)?.let {
                    templates.add(it)
                    templateRegistry.put(it.viewType, it)

//                    if (!templateRegistry.containsKey(it.viewType)) {
//                        templateRegistry.put(it.viewType, it)
//                    }
                }
            }
        }

        adapter.notifyDataSetChanged()
    }

    private fun appendLoadMoreIndicator() {
        if (autoLoad) {
            val indicator = GJsonObject.Default(hashMapOf("title" to "Loading..."))
            val template = ThumbnailV1(indicator, screen)
            templates.add(template)
            templateRegistry.put(template.viewType, template)
            loadMoreTemplate = template
            adapter.notifyDataSetChanged()
        }
    }

    private fun removeLoadMoreIndicator() {
        templates.remove(loadMoreTemplate)
    }

    private fun loadMore(url: String) {
        request?.cancel()
        request = null

        val callback = GRestCallback.Default(ProgressIndicator.NULL) { response ->
            val result = response.result
            removeLoadMoreIndicator()
            initNextPageInstructions(result)
            appendItems(result)
            appendLoadMoreIndicator()
        }
        request = Rest.GET.async(url, null, false).execute(callback)
    }

    fun detectScrollBottom() {
        val mScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val url = nextUrl
                if (!autoLoad || url == null) {
                    return
                }

                val lastVisiblePosition = this@ListV1.recyclerView.getLastCompletelyVisibleItemPos()
                val itemCount = adapter.getItemCount()
                if (lastVisiblePosition >= itemCount - 2 && lastVisiblePosition > 3) {
                    loadMore(url)
                }
            }
        }
        recyclerView.addOnScrollListener(mScrollListener)
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
