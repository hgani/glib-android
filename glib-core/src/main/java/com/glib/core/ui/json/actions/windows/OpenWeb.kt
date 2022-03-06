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
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.screen.GScreenContainer
import com.glib.core.screen.NavHelper
import com.glib.core.ui.icon.GIcon
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonUi
import com.glib.core.ui.menu.GMenu
import com.glib.core.utils.Res

class OpenWeb(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        val url = spec["url"].string ?: return false
        GLog.t(javaClass, "URL: " + UrlUtils.jsonToHtml(url))
        screen.launch.url(UrlUtils.jsonToHtml(url))
        return true
    }
}
