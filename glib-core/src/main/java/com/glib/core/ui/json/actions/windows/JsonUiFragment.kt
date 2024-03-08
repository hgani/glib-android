package com.glib.core.ui.json.actions.windows

import android.view.Menu
import android.view.MenuInflater
import com.glib.core.http.GRestCallback
import com.glib.core.http.Rest
import com.glib.core.http.UrlUtils
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.screen.GScreen
import com.glib.core.screen.GScreenContainer
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonUi
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.menu.GMenu

abstract class JsonUiFragment : GFragment {
    var path: String? = null
        private set
    protected var parseMenu = true
    private var page: GJson? = null

    constructor() {
        // Mandatory constructor
    }

    protected constructor(path: String, prependHost: Boolean) {
        setPath(path, prependHost)
    }

    protected fun setPath(path: String?, prependHost: Boolean) {
        path?.let {
            this.path = if (prependHost) UrlUtils.pathToUrl(it) else it
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        reload(path, {})
    }

    fun reload(path: String?, onReload: () -> Unit) {
        val callback = GRestCallback.Default(this@JsonUiFragment) { response ->
            val result = response.result
            JsonUi.parseScreenContent(result, this@JsonUiFragment)
            applyStyling(result)
            page = result

            screen?.let {
                updateNavBar(it, result["navBar"])
                it.invalidateOptionsMenu()
                updateDrawer(it, result["leftDrawer"])
            }

            onReload()
        }
        path?.let {
            Rest.GET.async(it, null, false).execute(callback)
        }
    }

    private fun applyStyling(spec: GJson) {
        JsonUiStyling.windows["default"]?.let {
            it.decorate(this)
        }

        val styleClasses = spec["styleClasses"].arrayValue.map { it.stringValue }
        for (styleClass in styleClasses) {
            JsonUiStyling.windows[styleClass]?.let {
                it.decorate(this)
            }
        }
    }

    private fun updateNavBar(screen: GScreen, spec: GJson) {
        val toolbar = screen.nav.toolbar

        JsonView.ifColor(spec["backgroundColor"].string) {
            toolbar.setBackgroundColor(it)
        }
        JsonView.ifColor(spec["color"].string) {
            toolbar.setTitleTextColor(it)
        }
    }

    private fun updateDrawer(screen: GScreen, spec: GJson) {
        if (spec.isNull()) {
            return
        }

        val nav = screen.nav
        nav.initLeftDrawer { menu ->
            spec["rows"].arrayValue.forEach { row ->
                menu.add(JsonUi.iconDrawable(row["icon"]), row["text"].stringValue) {
                    JsonAction.execute(row["onClick"], screen, null, null)
                }
            }
        }

        nav.showLeftIcon(nav.menuIcon())
    }

    override fun initContent(activity: GActivity, container: GScreenContainer) {
        // TODO: Fix. Doesn't work well with scroll panel.
//        enableRefreshPull()

        args[JsonUiScreen.ARG_ON_OPEN_SPEC].json?.let { onOpenSpec ->
            if (path == null) {
                JsonAction.execute(onOpenSpec, activity, null, null)
            } else {
                reload(path) {
                    JsonAction.execute(onOpenSpec, activity, null, null)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val page = this.page
        val activity = this.gActivity
        if (page == null || activity == null) {
            return
        }

        val gMenu = GMenu(menu)
        if (parseMenu) {
//            page["rightNavButtons"].arrayValue.forEach { button ->
//                val name = button["icon"]["materialName"].stringValue
//                gMenu.add(name) { item ->
//                    item.showIfRoom().onClick {
//                        JsonAction.execute(button["onClick"], activity, null, null)
//                    }
//                    JsonUi.iconDrawable(button["icon"])?.let { drawable ->
//                        item.icon(drawable)
//                    }
//                }
//            }

            page["navBar"]["rightButtons"].arrayValue.forEach { button ->
                val name = button["icon"]["material"]["name"].stringValue
                gMenu.add(name) { item ->
                    item.showIfRoom().onClick {
                        JsonAction.execute(button["onClick"], activity, null, null)
                    }
                    JsonUi.iconDrawable(button["icon"])?.let { drawable ->
                        item.icon(drawable)
                    }
                }
            }
        }

//        if (BuildConfig.DEBUG) {
//            gMenu.add("?") { item ->
//                item.showIfRoom().onClick {
//                    launch.alert(page.toString(), "Debug Info")
//                }
//            }
//        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
//        val page = this.page
//        val activity = this.gActivity
//        if (page == null || activity == null || menu == null) {
//            return
//        }
//
//        val gMenu = GMenu(menu)
//        if (parseMenu) {
//            page["rightNavButtons"].arrayValue.forEach { button ->
//                val name = button["icon"]["materialName"].stringValue
//                gMenu.add(name) { item ->
//                    item.showIfRoom().onClick {
//                        JsonAction.execute(button["onClick"], activity, null, null)
//                    }
//                    JsonUi.iconDrawable(button["icon"])?.let { drawable ->
//                        item.icon(drawable)
//                    }
//                }
//            }
//        }
//
//        if (BuildConfig.DEBUG) {
//            gMenu.add("?") { item ->
//                item.showIfRoom().onClick {
//                    launch.alert(page.toString())
//                }
//            }
//        }
//    }


    open class Spec(val decorator: (JsonUiFragment) -> Unit) {
        companion object {
//            val STANDARD = JsonUiFragment.Spec() { fragment ->
////                fragment.container?.bgColor(Color.RED)
//
//                // Nothing to do
//            }
        }

        fun decorate(view: JsonUiFragment) {
            decorator(view)
        }
    }
}
