package com.glib.core.screen

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.glib.core.R
import com.glib.core.ui.icon.GIcon
import com.glib.core.utils.Res.context
import com.google.android.material.navigation.NavigationView


open class NavHelper(protected val screen: GScreen): INavHelper() {
    private var hasLeftDrawer = false

    override final val layout: ViewGroup
    private val body: ViewGroup
    private val drawer: DrawerLayout
    private var selectedItem: MenuItem? = null
    private val badge: NavigationHomeBadge
    final val toolbar: Toolbar
    lateinit var actionBar: ActionBar


    init {
        this.layout = LayoutInflater.from(screen).inflate(R.layout.view_screen, null) as ViewGroup
        this.body = layout.findViewById<View>(R.id.screen_body) as ViewGroup
        this.drawer = layout.findViewById<View>(R.id.screen_drawer) as DrawerLayout
        this.badge = NavigationHomeBadge(screen)
        this.toolbar = layout.findViewById<View>(R.id.screen_toolbar) as Toolbar
    }

    fun openLeftDrawer() {
        drawer.openDrawer(GravityCompat.START)
    }

    fun openRightDrawer() {
        drawer.openDrawer(GravityCompat.END)
    }

    override final fun setBody(resId: Int) {
        LayoutInflater.from(context).inflate(resId, body)
    }

    ///// Navigation /////

    fun initNavigation(actionBar: ActionBar) {
        this.actionBar = actionBar

        initToolbar(toolbar)

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun initLeftDrawer(populate: (NavigationMenu) -> Unit) {
        this.hasLeftDrawer = true

        val leftNavView = drawer.findViewById<View>(R.id.view_navigation_left) as NavigationView
        val leftNavMenu = NavigationMenu(leftNavView.menu, actionBar)

        populate(leftNavMenu)

        val icon = screen.navMenuIcon()
        if (icon != null) {
            actionBar.setHomeAsUpIndicator(badge.drawable)
        }
    }

    fun initRightDrawer(populate: (NavigationMenu) -> Unit) {
        val rightNavView = drawer.findViewById<View>(R.id.view_navigation_right) as NavigationView
        val rightNavMenu = NavigationMenu(rightNavView.menu, actionBar)

        populate(rightNavMenu)
    }

    fun showHomeIcon() {
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    fun handleHomeClick(): Boolean {
        if (hasLeftDrawer) {
            openLeftDrawer()
            return true
        }
        return false
    }

    protected open fun initToolbar(toolbar: Toolbar) {
        // To be overridden
    }

//    // TODO: Remove
//    protected open fun initMenu(leftNavMenu: NavigationMenu, rightNavMenu: NavigationMenu) {
//        // To be overridden
//    }

    fun updateBadge(count: Int) {
        badge.setCount(count)
    }

//    // TODO: Allow customization
//    open fun menuIcon(): Drawable? {
//        return LibIcon.icon_menu.drawable().sizeDp(GIcon.ACTION_BAR_SIZE)
//    }

    /////


    inner class NavigationMenu internal constructor(private val menu: Menu, private val bar: ActionBar) {
        private fun intentEquals(menuIntent: Intent): Boolean {
            val activityIntent = screen.intent
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
            return add(icon?.drawable(), string, onClick)
        }

        fun add(iconDrawable: Drawable?, string: String, onClick: (MenuItem) -> Unit): MenuItem {
            val item = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, string)

            item.setOnMenuItemClickListener {
                onClick(it)
                true
            }
            iconDrawable?.let {
                item.icon = it
            }

            return item
        }
    }
}