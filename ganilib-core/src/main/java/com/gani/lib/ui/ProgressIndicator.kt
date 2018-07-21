package com.gani.lib.ui

import android.support.v4.widget.SwipeRefreshLayout

interface ProgressIndicator {
    fun showProgress()
    fun hideProgress()

    companion object {
        val NULL: ProgressIndicator = object : ProgressIndicator {
            override fun showProgress() {
                // Do nothing
            }

            override fun hideProgress() {
                // Do nothing
            }
        }
    }

    class Swipe(private val view: SwipeRefreshLayout): ProgressIndicator {
        override fun showProgress() {
            // Use post() so that this works when called from onCreateForScreen(), which is a common scenario (i.e. auto-populating list view)
            // See http://www.androidhive.info/2015/05/android-swipe-down-to-refresh-listview-tutorial/
            view.post {
                view.isRefreshing = true
            }
        }

        override fun hideProgress() {
            view.isRefreshing = false
        }
    }
}
