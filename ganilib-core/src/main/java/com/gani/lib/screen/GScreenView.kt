package com.gani.lib.screen

import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.*
import com.gani.lib.R
import com.gani.lib.ui.icon.GIcon
import com.gani.lib.utils.Res.context


open class GScreenView(protected val activity: GActivity) : INavHelper() {

    override val layout: ViewGroup
    private val body: ViewGroup
    private val drawer: DrawerLayout
    private var selectedItem: MenuItem? = null
//    private lateinit var leftNavMenu: NavigationMenu
//    private lateinit var rightNavMenu: NavigationMenu
    private val badge: NavigationHomeBadge
    override final val toolbar: Toolbar
    lateinit var actionBar: ActionBar


    init {
        this.layout = LayoutInflater.from(activity).inflate(R.layout.view_screen, null) as ViewGroup
        this.body = layout.findViewById<View>(R.id.screen_body) as ViewGroup
        this.drawer = layout.findViewById<View>(R.id.screen_drawer) as DrawerLayout
        this.badge = NavigationHomeBadge(this)
        this.toolbar = layout.findViewById<View>(R.id.screen_toolbar) as Toolbar
    }

    override fun openLeftDrawer() {
        drawer.openDrawer(GravityCompat.START)
    }

    override fun openRightDrawer() {
        drawer.openDrawer(GravityCompat.END)
    }

    override fun setBody(resId: Int) {
        LayoutInflater.from(context).inflate(resId, body)
    }

    ///// Navigation /////

    override final public fun initNavigation(topNavigation: Boolean, actionBar: ActionBar) {
        this.actionBar = actionBar

        initToolbar(toolbar)

        if (topNavigation) {
            val leftNavView = drawer.findViewById<View>(R.id.view_navigation_left) as NavigationView
            val leftNavMenu = NavigationMenu(leftNavView.menu, actionBar)
            val rightNavView = drawer.findViewById<View>(R.id.view_navigation_right) as NavigationView
            val rightNavMenu = NavigationMenu(rightNavView.menu, actionBar)

            initMenu(leftNavMenu, rightNavMenu)

            val icon = menuIcon()
            if (icon != null) {
                actionBar.setHomeAsUpIndicator(badge.drawable)
            }
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
//        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun showIcon() {
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    protected open fun initToolbar(toolbar: Toolbar) {
        // To be overridden
    }

    protected open fun initMenu(leftNavMenu: NavigationMenu, rightNavMenu: NavigationMenu) {
        // To be overridden
    }

    fun updateBadge(count: Int) {
        badge.setCount(count)
    }

    open fun menuIcon(): Drawable? {
        return null
    }

//    protected fun addMenu(icon: GIcon, label: String, intent: Intent): MenuItem {
//        return leftNavMenu.addItem(icon, label, intent)
//    }
//
//    protected fun addMenu(icon: GIcon, label: String, onClick: (MenuItem) -> Unit): MenuItem {
//        return leftNavMenu.addItem(icon, label, onClick)
//    }
    /////


    protected inner class NavigationMenu internal constructor(private val menu: Menu, private val bar: ActionBar) {
        private fun intentEquals(menuIntent: Intent): Boolean {
            val activityIntent = activity.intent
            if (activityIntent.component != menuIntent.component) {
                return false
            }

            val activityExtras = activityIntent.extras
            val menuExtras = menuIntent.extras

            if (activityExtras != null && menuExtras != null) {
                for (key in activityExtras.keySet()) {
                    if (activityExtras.get(key) != menuExtras.get(key)) {
                        return false
                    }
                }
            }
            return true
        }

        fun add(icon: GIcon?, string: String, intent: Intent): MenuItem {
            val item = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, string)
            item.intent = intent
            item.isCheckable = true  // Needed for setChecked() to work
            if (icon != null) {
                item.icon = icon.drawable()
            }

            if (intentEquals(intent)) {
                selectedItem = item
                item.isChecked = true
                bar.title = string
            }
            return item
        }

        fun add(icon: GIcon?, string: String, onClick: (MenuItem) -> Unit): MenuItem {
            val item = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, string)

            item.setOnMenuItemClickListener {
                onClick(it)
                true
            }
            if (icon != null) {
                item.icon = icon.drawable()
            }

            return item
        }
    }

//    companion object {
//        private val GROUP_PRIMARY = 1
//        private val GROUP_SECONDARY = 2
//    }
}