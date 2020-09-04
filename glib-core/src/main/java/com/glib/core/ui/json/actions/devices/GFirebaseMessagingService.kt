package com.glib.core.ui.json.actions.devices

import android.app.PendingIntent
import com.glib.core.GApp
import com.glib.core.http.GRestCallback
import com.glib.core.json.GJson
import com.glib.core.json.GJsonObject
import com.glib.core.logging.GLog
import com.glib.core.notification.Notifications
import com.glib.core.ui.ProgressIndicator
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.actions.windows.JsonUiScreen
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class GFirebaseMessagingService : FirebaseMessagingService() {
    private val DEFAULT_CHANNEL_ID = "GLIB_DEFAULT"
    private val DEFAULT_NOTIFICATION_ID = 0

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            val data = remoteMessage.data

            val channelId = data["channelId"] ?: DEFAULT_CHANNEL_ID
            val notificationId = data["notificationId"]?.toIntOrNull() ?: DEFAULT_NOTIFICATION_ID
            val openUrl = data["openUrl"]
            val onOpen = GJsonObject.Default(data["onOpen"])

//            showNotification(it.title, it.body, channelId, notificationId, onClick)
            showNotification(it.title, it.body, channelId, notificationId, openUrl, onOpen)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        val callback = GRestCallback.Default(ProgressIndicator.NULL) {
            // Can't do anything because we don't have access to Activity
        }
        GetPushToken.postToServer(token, callback)
    }

//    private fun showNotification(title: String?, body: String?, channelId: String, notificationId: Int, onClick: GJson) {
    private fun showNotification(title: String?, body: String?, channelId: String, notificationId: Int, openUrl: String?, onOpen: GJson) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT)
//
//        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder = NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setAutoCancel(true)
//                .setSound(soundUri)
//                .setContentIntent(pendingIntent)
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(0, notificationBuilder.build())

        val intent = if (openUrl == null) null else JsonUiScreen.intent(openUrl, false, onOpen)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val notification = Notifications.defaultBuilder(channelId)
                .setSmallIcon(GApp.INSTANCE.notificationIconRes())
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .build()
        Notifications.display(notificationId, notification)
    }
}