package com.glib.core.ui.json.actions.windows

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.Toolbar
import com.glib.core.BuildConfig
import com.glib.core.http.GRestCallback
import com.glib.core.http.Rest
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.screen.GScreenContainer
import com.glib.core.screen.NavHelper
import com.glib.core.ui.icon.GIcon
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonUi
import com.glib.core.ui.menu.GMenu
import com.glib.core.utils.Res
import com.mikepenz.google_material_typeface_library.GoogleMaterial

class OpenV1(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        val url = spec["url"].string
        if (url == null) {
            return false
        }
        screen.startActivity(JsonUiScreen.intent(url))
        return true
    }
}

class JsonUiScreen : GActivity() {
    companion object {
        val ARG_URL = "url"
        val ARG_SPEC = "spec"
        val ARG_ROOT = "isRoot"

        fun intent(url: String, isRoot: Boolean = false): Intent {
            return intentBuilder(JsonUiScreen::class)
                    .withArg(ARG_URL, url)
                    .withArg(ARG_ROOT, isRoot).intent
        }

        fun intent(actionSpec: GJson): Intent {
            return intentBuilder(JsonUiScreen::class)
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    .withArg(ARG_SPEC, actionSpec).intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = NavHelper(this)
        super.onCreateForScreen(savedInstanceState, view)
        setFragmentWithToolbar(createNewIntentFragment(), false, savedInstanceState)

        initNavigation()
    }

    override fun createNewIntentFragment(): GFragment {
        return ContentFragment()
    }

    private fun initNavigation() {
        if (!args[JsonUiScreen.ARG_ROOT].boolValue) {
            nav.showHomeIcon()
        }
    }



    class ContentFragment : JsonUiFragment() {
        override fun initContent(activity: GActivity, container: GScreenContainer) {
            super.path = args[ARG_URL].string
            super.prependHost = false
            super.parseMenu = true
            super.initContent(activity, container)
        }

    }
}

abstract class JsonUiFragment : GFragment {
    protected var path: String? = null
    protected var prependHost: Boolean = false
    protected var parseMenu = true
    private var page: GJson? = null

    constructor() {
        // Mandatory constructor
    }

    protected constructor(path: String, prependHost: Boolean) {
        this.path = path
        this.prependHost = prependHost
    }

    override fun onRefresh() {
        super.onRefresh()

        val callback = GRestCallback.Default(this@JsonUiFragment) { response ->
            JsonUi.parseScreenContent(response.result, this@JsonUiFragment)
            page = response.result
            activity?.invalidateOptionsMenu()
        }
        path?.let { path ->
            Rest.GET.async(path, null, prependHost).execute(callback)
        }
    }

    override fun initContent(activity: GActivity, container: GScreenContainer) {
        onRefresh()

        args[JsonUiScreen.ARG_SPEC].json?.let {
            JsonAction.execute(it, activity, null, null)
            activity.finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        val page = this.page
        val activity = this.gActivity
        if (page == null || activity == null || menu == null) {
            return
        }

        val gMenu = GMenu(menu)
        if (parseMenu) {
            page["rightNavButtons"].arrayValue.forEach { button ->
                val name = button["icon"]["materialName"].stringValue
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

        if (BuildConfig.DEBUG) {
            gMenu.add("?") { item ->
                item.showIfRoom().onClick {
                    launch.alert(page.toString())
                }
            }
        }
    }
}


// TODO
//internal class MyNavHelper(activity: GActivity) : NavHelper(activity) {
//    override fun initToolbar(toolbar: Toolbar) {
////        toolbar.setBackgroundColor(MyColor.NAVBAR_BG)
////        toolbar.setTitleTextColor(MyColor.NAVBAR_TEXT)
//    }
//
//    override fun initMenu(leftNavMenu: NavigationMenu, rightNavMenu: NavigationMenu) {
//        leftNavMenu.add(MyIcon.icon_home, Res.str(R.string.nav_home), HomeMenuScreen.intent())
//        leftNavMenu.add(MyIcon.icon_cart, Res.str(R.string.nav_merchants), MerchantListScreen.intent(MerchantListScreen.TAB_ALL))
//        leftNavMenu.add(MyIcon.icon_calendar, Res.str(R.string.nav_events), EventListScreen.intent())
//        leftNavMenu.add(MyIcon.icon_attraction, Res.str(R.string.nav_attractions), AttractionListScreen.intent())
//
//        if (BuildConfig.DEBUG) {
//            leftNavMenu.add(MyIcon.icon_question, "Diagnostics", JsonUiScreen.intent(MyBuild.INSTANCE.host + "/app_diagnostics.json"))
//        }
//
////            addMenu(MyIcon.icon_home, "Offers (testing only)", OfferListScreen.intent())
////            addMenu(MyIcon.icon_qr, "Checkins", CheckinScreen.intent())
//    }
//
//    override fun menuIcon(): Drawable? {
////        return MyIcon.icon_menu.drawable().color(MyColor.NAVBAR_TEXT).sizeDp(GIcon.ACTION_BAR_SIZE)
//        return GoogleMaterial.Icon.gmd_menu.draw
//    }
//}