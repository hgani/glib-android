package com.glib.core.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
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
import com.glib.core.json.GJsonObject
import com.glib.core.logging.GLog
import com.glib.core.model.GBundle
import com.glib.core.ui.ProgressIndicator
import com.glib.core.ui.icon.GIcon
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.actions.windows.Close
import com.glib.core.ui.json.actions.windows.JsonUiScreen
import com.glib.core.ui.style.LibIcon
import com.glib.core.utils.Res
import java.io.Serializable
import kotlin.reflect.KClass

open class GScreen : GActivity() {
    val nav: NavHelper
        get() = inav as NavHelper

    val navBar: ActionBar
        get() = supportActionBar!!  // It should exist because setSupportActionBar() should have been called

    fun onCreateForScreen(savedInstanceState: Bundle?) {
        super.initOnCreate(NavHelper(this))
        setSupportActionBar(nav.toolbar)
    }

    fun setFragmentWithToolbar(fragment: GFragment?, savedInstanceState: Bundle?) {
//        this.topNavigation = topNavigation
//        if (fragment != null) {
//            setFragment(fragment, savedInstanceState)
//        }

        setFragmentWithoutToolbar(fragment, savedInstanceState)
        nav.initNavigation(navBar)
        nav.toolbar.visibility = View.VISIBLE

//        val toolbar = nav.toolbar
//        if (toolbar != null) {
//            toolbar.visibility = View.VISIBLE
//        }
    }

    fun setContentWithToolbar(resId: Int) {
//        this.topNavigation = topNavigation
//        setContent(resId)

        setContentWithoutToolbar(resId)
        nav.initNavigation(navBar)
        nav.toolbar.visibility = View.VISIBLE

//        val toolbar = nav.toolbar
//        if (toolbar != null) {
//            toolbar.visibility = View.VISIBLE
//        }
    }

    fun handleStartingScreen(intent: Intent) {
        // Sometimes a starting screen needs to:
        // - Stay open so that it can execute stuff in the background (e.g. handle beacon scans)
        // - Closes itself when it receives result from the last screen
        startActivityForResult(intent, 0)

        val data = args
        val openUrl = data["openUrl"].string
        val onOpen = GJsonObject.Default(data["onOpen"].string)

        openUrl?.let {
            startActivity(JsonUiScreen.intent(it, false, onOpen))
        }
    }



    ///// Menu /////

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (!nav.handleHomeClick()) {
                    onBackPressed()  // Going up is more similar to onBackPressed() than finish(), especially because the former can have pre-finish check
                }
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
