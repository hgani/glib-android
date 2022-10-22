package com.glib.core.ui.menu

import android.view.Menu

class GMenu(private val backend: Menu) {
    fun hasVisibleItems(): Boolean {
        return backend.hasVisibleItems()
    }

    fun add(str: String, init: (GMenuItem) -> Unit): GMenu {
        init(GMenuItem(backend.add(Menu.NONE, Menu.NONE, Menu.NONE, str)))
        return this
    }

//    fun addSecondary(strId: Int, order: Int, listener: OnClickListener): MenuItem {
//        val item = menu!!.add(Menu.NONE, Menu.NONE, Menu.CATEGORY_CONTAINER + ORDER_UNIMPORTANT + order, strId)
//        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
//        item.setOnMenuItemClickListener(listener)
//        return item
//    }
//
//    fun addBlankSecondary(): MenuItem {
//        val item = menu!!.add(Menu.NONE, Menu.NONE, Menu.CATEGORY_CONTAINER + ORDER_UNIMPORTANT + GMenu.ORDER_SPECIFIC, "")
//        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
//        return item
//    }
//
//    companion object {
//        val ORDER_UNIMPORTANT = 1000
//        val ORDER_SPECIFIC = 10
//        val ORDER_COMMON = 20
//    }
}