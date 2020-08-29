package com.glib.core.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.glib.core.utils.Res
import com.glib.core.utils.Res.context

object Notifications {
    private val CHANNEL_NAME = "Default Channel Name"
    private val CHANNEL_DESCRIPTION = "Default Channel Description"

    fun defaultBuilder(channelId: String): NotificationCompat.Builder {
        createChannel(channelId)
        return NotificationCompat.Builder(context, channelId)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
    }

    fun createChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel(channelId)
    }

    fun display(id: Int, notification: Notification) {
        manager().notify(id, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String) {
        val name = CHANNEL_NAME
        val description = CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(channelId, name, importance)
        mChannel.description = description

        manager().createNotificationChannel(mChannel)
    }

    private fun manager(): NotificationManager {
        return Res.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}