package com.glib.core.compose.model

import com.glib.core.compose.core.extensions.nullableString
import org.json.JSONObject

data class H1Model(
    override val type: String,
    override val text: String,
) : TextModel(type, text) {

    companion object {
        fun map(jsonObject: JSONObject): H1Model {
            val type = jsonObject.nullableString("view") ?: ""
            val text = jsonObject.nullableString("text") ?: ""

            return H1Model(
                type = type,
                text = text
            )
        }
    }
}
