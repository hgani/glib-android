package com.glib.core.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.glib.core.R
import com.glib.core.logging.GLog
import com.glib.core.model.GBundle
import com.glib.core.ui.ProgressIndicator
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.actions.windows.Close
import com.glib.core.utils.Res
import java.io.Serializable
import kotlin.reflect.KClass

open class GDialog : GActivity() {
    // Unfortunately when setting theme programatically, the background won't be transparent. See http://stackoverflow.com/questions/15455979/translucent-theme-does-not-work-when-set-programmatically-on-android-2-3-3-or-4
    // So we'll stick to setting theme in manifest and calling the right dialog method here. Ideally both can be done in this method.
    //
    // In the future, if we want to look into this again, beware that theming is time consuming and causes weird errors (with useless stacktraces).
    //
    // Put this in themes.xml:
    // <style name="FakeDialog" parent="Theme.AppCompat.Light.Dialog">
    //   <item name="windowNoTitle">true</item>
    // </style>
    protected fun onCreateForDialog(savedInstanceState: Bundle?) {
        initOnCreate(object : INavHelper() {
            override val layout = LayoutInflater.from(context).inflate(R.layout.barebone_view_screen, null) as ViewGroup
            override fun setBody(resId: Int) {
                LayoutInflater.from(context).inflate(resId, layout)
            }
        })
    }
}
