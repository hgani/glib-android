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
import com.glib.core.http.UrlUtils
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

class Open(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        val url = spec["url"].string
        if (url == null) {
            return false
        }
        screen.startActivityForResult(JsonUiScreen.intent(url, false, false), 0)
        return true
    }
}



//internal class MyNavHelper(activity: GActivity) : NavHelper(activity) {
//    override fun initToolbar(toolbar: Toolbar) {
//        toolbar.setBackgroundColor(MyColor.NAVBAR_BG)
//        toolbar.setTitleTextColor(MyColor.NAVBAR_TEXT)
//    }
//
//    override fun initMenu(leftNavMenu: NavigationMenu, rightNavMenu: NavigationMenu) {
//        leftNavMenu.add(MyIcon.icon_home, Res.str(R.string.nav_home), HomeMenuScreen.intent())
//        leftNavMenu.add(MyIcon.icon_cart, Res.str(R.string.nav_merchants), MerchantListScreen.intent(MerchantListScreen.TAB_ALL))
//        leftNavMenu.add(MyIcon.icon_calendar, Res.str(R.string.nav_events), EventListScreen.intent())
//        leftNavMenu.add(MyIcon.icon_attraction, Res.str(R.string.nav_attractions), AttractionListScreen.intent())
//        leftNavMenu.add(MyIcon.icon_scan, Res.str(R.string.nav_scans), ScanListScreen.intent())
//        leftNavMenu.add(MyIcon.icon_safety, Res.str(R.string.nav_safety)) {
//            activity.launch.url(Res.str(R.string.safety_url))
//        }
//
//        if (BuildConfig.DEBUG) {
////            leftNavMenu.add(MyIcon.icon_question, "Diagnostics", JsonUiScreen.intent(MyBuild.INSTANCE.host + "/app_diagnostics.json"))
//        }
//
////            addMenu(MyIcon.icon_home, "Offers (testing only)", OfferListScreen.intent())
////            addMenu(MyIcon.icon_qr, "Checkins", CheckinScreen.intent())
//    }
//
//    override fun menuIcon(): Drawable? {
//        return MyIcon.icon_menu.drawable().color(MyColor.NAVBAR_TEXT).sizeDp(GIcon.ACTION_BAR_SIZE)
//    }
//}