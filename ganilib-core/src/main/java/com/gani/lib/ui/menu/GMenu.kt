package com.gani.lib.ui.menu

import android.view.Menu

class GMenu(private val backend: Menu) {
    fun hasVisibleItems(): Boolean {
        return backend.hasVisibleItems()
    }

    fun add(str: String, init: (GMenuItem) -> Unit): GMenu {
        init(GMenuItem(backend.add(Menu.NONE, Menu.NONE, Menu.NONE, str)))
        return this
    }

//    fun add(str: String, icon: GIcon?, showAsAction: Int?, listener: OnClickListener?): MenuItem {
//        val item = menu!!.add(Menu.NONE, Menu.NONE, Menu.NONE, str)
//        if (showAsAction != null) {  // Not applicable for certain types of menu, e.g. popup menu.
//            item.setShowAsAction(showAsAction)
//        }
//        if (icon != null) {
//            item.icon = icon.drawable().sizeDp(GIcon.ACTION_BAR_SIZE)
//        }
//        if (listener != null) {
//            item.setOnMenuItemClickListener(listener)
//        }
//        return item
//    }
//
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