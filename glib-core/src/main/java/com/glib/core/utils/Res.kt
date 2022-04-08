package com.glib.core.utils

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.preference.PreferenceManager
import com.glib.core.logging.GLog
import com.glib.core.prefs.Prefs
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object Res {
    private var appContext: Any? = null  // Store as Any to avoid compile warning

    fun init(context: Context) {
        appContext = context
    }

    // This won't interfere with prefs from other projects. See https://stackoverflow.com/questions/36777401/does-sharedpreferences-name-need-to-be-unique
    private val PREF_NAME = "__glib_prefs"

    val context: Context
        get() = appContext as Context

    val resources: Resources
        get() = context.resources

    fun prefs(name: String): Prefs {
        return Prefs(context.getSharedPreferences(name, Context.MODE_PRIVATE))
    }

    fun libPrefs(): Prefs {
        return prefs(PREF_NAME)
    }

    fun defaultPrefs(): Prefs {
        return Prefs(PreferenceManager.getDefaultSharedPreferences(context))
    }

    private fun assets(): AssetManager {
        return context.assets
    }

    @Throws(IOException::class)
    fun assetText(fileName: String): String {
        val `is` = assets().open(fileName)

//        val reader = BufferedReader(InputStreamReader(`is`))
//        val out = StringBuilder()
//        var line: String
//        while ((line = reader.readLine()) != null) {
//            out.append(line)
//        }
//        reader.close()

        val out = StringBuilder()
        BufferedReader(InputStreamReader(`is`)).use { r ->
            r.lineSequence().forEach {
                out.append(it)
            }
        }

        return out.toString()
    }

    @Throws(JSONException::class)
    fun assetJsonObject(path: String): JSONObject {
        try {
            val jsonAsString = assetText(path)
            return JSONObject(jsonAsString)
        } catch (e: IOException) {
            throw JSONException(e.message)
        }

    }

    @Throws(IOException::class)
    fun assetDrawable(fileName: String): Drawable {
        val ims = assets().open(fileName)
        return Drawable.createFromStream(ims, null)
        //    try {
        //      // put image to ImageView
        //      mImage.setImageDrawable(d);
        //    }
        //    catch(IOException ex) {
        //      return;
        //  }
    }

    // Load a font file from `app/src/main/assets/font`
    fun font(font: String): Typeface {
        return Typeface.createFromAsset(context.assets, "font/$font")
    }

//    fun font(fontResId: Int): Typeface {
//        return Typeface.createFromAsset(context.assets, fontResId)
//    }

    fun integer(resId: Int): Int {
        return resources.getInteger(resId)
    }

    fun dimen(resId: Int): Int {
        return resources.getDimension(resId).toInt()
    }

    fun color(resId: Int): Int {
        return resources.getColor(resId)
    }

    fun drawable(resId: Int): Drawable? {
        return context.getDrawable(resId)
    }

    fun str(resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

    fun str(key: String, vararg formatArgs: Any): String {
        val resId = resources.getIdentifier(key, "string", context.packageName)
        return context.getString(resId, *formatArgs)
    }

    fun quantityStr(resId: Int, quantity: Int, vararg formatArgs: Any): String {
        return resources.getQuantityString(resId, quantity, *formatArgs)
    }

    private fun expandColorIfNecessary(code: String): String {
        if (code.length == 3) {
            var result = ""
            for (c in code.toCharArray()) {
                result += "" + c + c
            }
            return result
        }
        if (code.length == 8) {
            return code.substring(6) + code.substring(0, 6)
        }

        return code
    }

    @Throws(IllegalArgumentException::class)
    fun color(code: String?): Int {
        var code = code

        if (code != null) {
            if (code.startsWith("#")) {
                code = "#" + expandColorIfNecessary(code.substring(1))
            }
            try {
                return Color.parseColor(code)
            } catch (e: IllegalArgumentException) {
                throw e
            } catch (e: StringIndexOutOfBoundsException) {
                throw IllegalArgumentException(e)
            }

        }
        throw IllegalArgumentException()
    }

//    fun color(code: String?): Int? {
//        var newCode = code
//
//        if (code != null) {
//            if (code.startsWith("#")) {
//                newCode = "#" + expandColorIfNecessary(code.substring(1))
//            }
//            try {
//                return Color.parseColor(newCode)
//            } catch (e: IllegalArgumentException) {
//                return null
////                throw e
//            } catch (e: StringIndexOutOfBoundsException) {
//                return null
////                throw IllegalArgumentException(e)
//            }
//
//        }
////        throw IllegalArgumentException()
//        return null
//    }
}
