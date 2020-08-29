package com.glib.core.notification

import android.view.Gravity
import android.widget.Toast
import com.glib.core.screen.GActivity
import com.glib.core.utils.Res
import com.google.android.material.snackbar.Snackbar

object SnackbarUtils {
    fun standard(activity: GActivity, string: String): Snackbar {
        return Snackbar.make(activity.findViewById(android.R.id.content), string, Snackbar.LENGTH_SHORT)
    }
}
