package com.glib.core.notification

import android.view.Gravity
import android.widget.Toast
import com.glib.core.utils.Res

object Toast {
//    // Can be run from a background thread, e.g. HttpCallback.Rest.onRestSuccess()
//    @JvmOverloads
//    fun showNormal(str: String, duration: Int = Toast.LENGTH_SHORT) {
//        val mainHandler = Handler(Res.context.mainLooper)
//        val myRunnable = Runnable { Toast.makeText(Res.context, str, duration).show() }
//        mainHandler.post(myRunnable)
//    }
//
//    fun showNormal(strId: Int) {
//        showNormal(Res.str(strId))
//    }
//
//    fun showToastInCenter(strId: Int) {
//        showToastInCenter(Res.str(strId))
//    }

    fun center(string: String) {
        val toast = Toast.makeText(Res.context, string, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, Res.resources.displayMetrics.heightPixels / 2)
        toast.show()
    }
}
