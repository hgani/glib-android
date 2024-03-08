package com.glib.core.compose.model

import android.util.Log
import com.glib.core.compose.components.EmptyModel
import com.glib.core.compose.core.extensions.nullableString
import org.json.JSONObject

// TODO: Remove
interface ComponentModel {
    val type: String
}

fun getComponentModel(jsonObject: JSONObject): ComponentModel {
    val type = jsonObject.nullableString("view") ?: ""
    Log.d("TEST1", "getComponentModel1: " + type + " -- " + type.removeSuffix("-v1"))
    return when (type.removeSuffix("-v1")) {
        "panels/scroll" -> {
            Log.d("TEST1", "getComponentModel2")
            ScrollPanelModel.map(jsonObject)
        }
        "h1" -> {
            Log.d("TEST1", "getComponentModel2")
            H1Model.map(jsonObject)
        }
        else -> {
            EmptyModel.map(jsonObject)
        }
    }
}
