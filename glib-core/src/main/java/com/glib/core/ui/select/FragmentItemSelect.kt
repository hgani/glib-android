package com.gani.lib.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TabHost
import android.widget.TabHost.OnTabChangeListener
import androidx.recyclerview.widget.RecyclerView
import com.glib.core.R
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.screen.GScreenContainer
import com.glib.core.ui.select.SelectableItem
import com.glib.core.ui.view.ViewHelper

abstract class FragmentItemSelect<I : SelectableItem?> protected constructor(
//    helper: ViewHelper?
) : GFragment() {
//    private val tabHelper: ViewHelper?
//    private var selectedTab: T? = null

    private fun onCreateView(
        inflater: LayoutInflater?,
        layoutId: Int,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentLayout = inflater!!.inflate(layoutId, null)
//        selectedTab = tabHelper.initView(
//            savedInstanceState, getActivity(),
//            fragmentLayout!!.findViewById<View?>(R.id.tabhost) as TabHost?,
//            inflater.inflate(R.layout.tabcontent_common_list, null)
//        )
//        initTabsIn(inflater)

        val listView: RecyclerView = fragmentLayout.findViewById(R.id.list_common) as RecyclerView
        initList(listView)
        return fragmentLayout
    }

//    fun initContent(activity: GActivity, container: GLinearLayout) {
////    super.initContent(activity, container);
//        container.append(
//            LayoutInflater.from(getContext()).inflate(R.layout.fragment_common_list, null)
//        )
//
////    View.inflate(getContext(), )
//    }

    override fun initContent(activity: GActivity, container: GScreenContainer) {
        container.append(
            LayoutInflater.from(getContext()).inflate(R.layout.fragment_common_list, null)
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            onRefresh()
        }
    }

//    override fun onRefresh() {
//        super.onRefresh()
//    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        tabHelper.save(outState, selectedTab)
//    }

//    fun onDestroy() {
//        super.onDestroy()
//    }

//    private fun initTabsIn(inflater: LayoutInflater?) {
//        val listView: RecyclerView = tabHelper.findView(R.id.list_common) as RecyclerView
//
////        tabHelper.initTabs(TabContentSwitcher(), selectedTab)
//        initList(listView)
//    }

    protected abstract fun initList(listView: RecyclerView?)

//    private inner class TabContentSwitcher : OnTabChangeListener {
//        override fun onTabChanged(topicTabName: String?) {
//            selectedTab = tabHelper.getTab(topicTabName)
//        }
//    }

    companion object {
        val RETURN_ITEMS = "items"
    }

//    init {
//        tabHelper = helper
//    }
}