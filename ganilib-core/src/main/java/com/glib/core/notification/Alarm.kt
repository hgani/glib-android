package com.glib.core.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.glib.core.utils.Res
import com.glib.core.utils.Res.context

// NOTE: Use BOOT_COMPLETED to reschedule upon reboot
class Alarm<T: BroadcastReceiver> {
    private val intent: PendingIntent

    // Make sure to use the same requestCode to cancel
    constructor (requestCode: Int, target: Class<T>) {
        this.intent = Intent(Res.context, target).let { intent ->
            PendingIntent.getBroadcast(Res.context, requestCode, intent, 0)
        }
    }

    private fun manager(): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun scheduleWithIcon(scheduledInMillis: Long) {
        val info = AlarmManager.AlarmClockInfo(scheduledInMillis, intent)
        manager().setAlarmClock(info, intent)
    }

    fun schedule(scheduledInMillis: Long, repeatInMillis: Long? = null) {
        val manager = manager()

        if (repeatInMillis != null) {
            manager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduledInMillis,
                    repeatInMillis,
                    intent
            )
        }
        else {
            manager.set(
                    AlarmManager.RTC_WAKEUP,
                    scheduledInMillis,
                    intent)
        }

        // Old SDK: https://stackoverflow.com/questions/48603910/alarm-manager-when-set-alarm-show-alarm-icon-in-right-taskbar
//        // Set alarm icon on taskbar
//        val alarmChanged = Intent("android.intent.action.ALARM_CHANGED")
//        alarmChanged.putExtra("alarmSet", true)
//        context.sendBroadcast(alarmChanged)
    }

    fun cancel() {
        manager().cancel(intent)

        // Old SDK: https://stackoverflow.com/questions/48603910/alarm-manager-when-set-alarm-show-alarm-icon-in-right-taskbar
//        // Unset alarm icon
//        val alarmChanged = Intent("android.intent.action.ALARM_CHANGED")
//        alarmChanged.putExtra("alarmSet", false)
//        context.sendBroadcast(alarmChanged)
    }
}
