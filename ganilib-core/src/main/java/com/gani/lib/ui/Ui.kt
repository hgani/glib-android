package com.gani.lib.ui

import android.os.Handler

object Ui {
    private lateinit var uiHandler: Handler

    fun init(handler: Handler) {
        uiHandler = handler
    }

    // Use this to:
    // Ensure that async task is created and executed on UI thread and
    // Ensure that listeners are executed on UI thread
    fun run(command: Runnable) {
        uiHandler.post(command)
    }

    fun run(command: Runnable, delayInMillis: Int) {
        uiHandler.postDelayed(command, delayInMillis.toLong())
    }
}
