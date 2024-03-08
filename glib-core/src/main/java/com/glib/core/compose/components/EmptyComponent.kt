package com.glib.core.compose.components

import androidx.compose.runtime.Composable
import com.glib.core.compose.model.ComponentModel
import org.json.JSONObject

// When no component match than use this one
@Composable
fun EmptyComponent(model: JSONObject) {
}

data class EmptyModel(override val type: String = "empty"): ComponentModel {
    companion object {
        fun map(jsonObject: JSONObject): EmptyModel {
            return EmptyModel()
        }
    }
}