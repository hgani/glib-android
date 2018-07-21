package com.gani.lib.screen

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.TelephonyManager
import com.gani.lib.logging.GLog

class LaunchHelper(private val context: Context) {
    fun map(address: String) {
        val uri = "http://maps.google.com/maps?q=" + address
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }

    fun call(number: String) {
        if ((context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).phoneType != TelephonyManager.PHONE_TYPE_NONE) {
            val i = Intent(Intent.ACTION_DIAL)
            i.data = Uri.parse("tel:" + number)
            context.startActivity(i)
        } else {
            alert("This device doesn't support phone calls")
        }
    }

    fun mail(to: String, subject: String, message: String) {
        val i = Intent(Intent.ACTION_SEND)
        val s = arrayOf(to)
        i.putExtra(Intent.EXTRA_EMAIL, s)
        i.putExtra(Intent.EXTRA_SUBJECT, subject)
        i.putExtra(Intent.EXTRA_TEXT, message)
        i.type = "message/rfc822"
        val chooser = Intent.createChooser(i, "Launch Email")
        context.startActivity(chooser)
    }

    fun url(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            context.startActivity(browserIntent)
        } catch (e: ActivityNotFoundException) {
            GLog.e(javaClass, "Invalid URL: " + url)
        }
    }

    fun alert(message: String, title: String? = null) {
        val builder = AlertDialog.Builder(context)

        if (title != null) {
            builder.setTitle(title)
        }

        builder.setMessage(message).show()
    }
}
