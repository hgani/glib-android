package com.gani.lib.ui.datetime

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.TimePicker
import com.gani.lib.screen.GFragment
import java.util.*

class TimePickerFragment : DialogFragment() {
    companion object {
        fun show(target: GFragment, requestCode: Int) {
            val picker = TimePickerFragment()
            picker.show(target.fragmentManager, "timePicker")
//            datePicker.setTargetFragment(target, requestCode)
//            datePicker.show(target.fragmentManager, "datePicker");
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time = Calendar.getInstance()
        val hour = time.get(Calendar.HOUR_OF_DAY)
        val minute = time.get(Calendar.MINUTE)

        val callback: (TimePicker, Int, Int) -> Unit = { picker, hour, minute ->
//            val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val intent = Intent(activity, AlarmReceiver::class.java).let { intent ->
//                intent.putExtra("CHANNEL_ID", (activity as MainActivity).CHANNEL_ID)
//                PendingIntent.getBroadcast(activity, 0, intent, 0)
//            }
//
//            (activity as MainActivity).alarmManager = alarmManager
//            (activity as MainActivity).alarmIntent = intent
//
//            val calendar: Calendar = Calendar.getInstance().apply {
//                timeInMillis = System.currentTimeMillis()
//                set(Calendar.HOUR_OF_DAY, hour)
//                set(Calendar.MINUTE, minute)
//            }

//            alarmManager?.setRepeating(
//                    AlarmManager.RTC_WAKEUP,
//                    calendar.timeInMillis,
//                    1000 * 60 * 1,
//                    intent
//            )
//
//            // Set alarm icon on taskbar
//            val alarmChanged = Intent("android.intent.action.ALARM_CHANGED")
//            alarmChanged.putExtra("alarmSet", true)
//            (activity as MainActivity).sendBroadcast(alarmChanged)
        }

        return TimePickerDialog(activity, callback, hour, minute, true)
    }
}