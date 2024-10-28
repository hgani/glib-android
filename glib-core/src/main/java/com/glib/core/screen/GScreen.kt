package com.glib.core.screen

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import com.glib.core.json.GJsonObject
import com.glib.core.ui.json.actions.windows.JsonUiScreen

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
