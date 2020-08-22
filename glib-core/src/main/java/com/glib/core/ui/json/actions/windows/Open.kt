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
