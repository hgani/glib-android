package com.gani.lib.notification

import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.gani.lib.utils.Res
import com.gani.lib.utils.Res.context

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String) {
        val name = CHANNEL_NAME
        val description = CHANNEL_DESCRIPTION
        val importance = android.app.NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(channelId, name, importance)
        mChannel.description = description
        val mNotificationManager = Res.context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        mNotificationManager.createNotificationChannel(mChannel)
    }
}