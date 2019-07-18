package com.glib.core.ui

import android.os.Handler
import android.os.Looper


object Ui {
    val uiHandler = Handler(Looper.getMainLooper())

    // Use this to:
    // Ensure that async task is created and executed on UI thread and
    // Ensure that listeners are executed on UI thread
    fun exec(command: () -> Unit) {
        uiHandler.post(command)
    }

    fun exec(command: Runnable, delayInMillis: Int) {
        uiHandler.postDelayed(command, delayInMillis.toLong())
    }
}
