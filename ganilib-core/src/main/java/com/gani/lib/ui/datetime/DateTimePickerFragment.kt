package com.gani.lib.ui.datetime

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import com.gani.lib.model.GBundle
import com.gani.lib.screen.GFragment
import java.util.*

class DateTimePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    companion object {
        fun show(target: GFragment, requestCode: Int) {
            val datePicker = DateTimePickerFragment()
            datePicker.setTargetFragment(target, requestCode)
//            datePicker.setTargetFragment(target, ContentFragment.REQUEST_ONCE_DATE)
            datePicker.show(target.fragmentManager, "datePicker");
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time = Calendar.getInstance()
        val year = time.get(Calendar.YEAR)
        val month = time.get(Calendar.MONTH)
        val date = time.get(Calendar.DATE)
        return DatePickerDialog(context!!, this, year, month, date)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val date = calendar.time

        val intent = Intent()
        intent.putExtra(GBundle.KEY_SINGLETON, date)

        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
    }
}