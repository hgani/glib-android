package com.glib.core.compose.model

import com.glib.core.compose.core.extensions.nullableString
import org.json.JSONObject

abstract class TextModel(
    override val type: String,
//    val isScrollable: Boolean,
    open val text: String,
//    val height: Int,
//    @FloatRange val aspectRatio: Float?,
//    val itemSpacing: Int,
//    val horizontalPadding: Int,
//    val listItems: List<SectionItem.EmbeddedListItem>,// This list is flattened from nested list as there is no edge case where it do not required to flatten
) : ComponentModel {

//    fun importFrom(jsonObject: JSONObject) {
//        this.type = jsonObject.nullableString("view") ?: ""
//        text = jsonObject.nullableString("text") ?: ""
//    }

    companion object {
//        fun <T : TextModel>map(jsonObject: JSONObject): T {
//            val type = jsonObject.nullableString("view") ?: ""
//            val text = jsonObject.nullableString("text") ?: ""
//            return T(
//                type = type,
//                text = text
//            )
//        }
    }
}
