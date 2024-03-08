package com.glib.core.compose.model

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.glib.core.compose.core.extensions.nullableColor
import com.glib.core.compose.core.extensions.nullableObject
import com.glib.core.compose.core.extensions.nullableString
import com.glib.core.compose.core.extensions.nullableObjectList
import com.glib.core.compose.core.extensions.string
import org.json.JSONObject

// We're avoiding mapping of "component" as it is being mapped and handled in relevant class
// We're avoiding mapping of "navMenu", "onLoad", "onFocus" for now, once we needed we can map them
data class WindowModel(
//    val section: String,
    val title: String,
//    val contentTitle: String,
//    val webUrl: String,
//    val barColor: Color,
//    val navTintColor: Color,
//    val id: Int,
//    val cacheKey: String,
//    val scrollableNavBar: Boolean,
//    val reloadWindowOnFocus: Boolean,
//    val toolbarType: String,

    val body: JSONObject?
) {

    companion object {
        fun map(jsonObject: JSONObject): WindowModel {
//            val section = jsonObject.nullableString("section") ?: ""
//            val title = jsonObject.nullableString("title") ?: ""
//            val contentTitle = jsonObject.nullableString("contentTitle") ?: ""
//            val webUrl = jsonObject.nullableString("webUrl") ?: ""
//            val barColor = jsonObject.nullableColor("barColor") ?: Color.Transparent
//            val navTintColor = jsonObject.nullableColor("navTintColor") ?: Color.Transparent
//            val id = jsonObject.nullableString("id")?.toInt() ?: 0
//            val cacheKey = jsonObject.nullableString("cacheKey") ?: ""
//            val scrollableNavBar =
//                jsonObject.nullableString("scrollableNavBar")?.toBoolean() ?: false
//            val reloadWindowOnFocus =
//                jsonObject.nullableString("reloadWindowOnFocus")?.toBoolean() ?: false
//            val toolbarType = jsonObject.nullableString("toolbarType") ?: ""

            return WindowModel(
                title = jsonObject.string("title"),
                body = jsonObject.nullableObject("body")
            )
        }
    }
}
