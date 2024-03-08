package com.glib.core.compose.model

import com.glib.core.compose.core.extensions.nullableString
import com.glib.core.compose.core.extensions.nullableObjectList
import org.json.JSONObject

data class ScrollPanelModel(
    override val type: String,
//    val isScrollable: Boolean,
//    val direction: String,
//    val height: Int,
//    @FloatRange val aspectRatio: Float?,
//    val itemSpacing: Int,
//    val horizontalPadding: Int,
//    val listItems: List<SectionItem.EmbeddedListItem>,// This list is flattened from nested list as there is no edge case where it do not required to flatten

    val childItems: List<ComponentModel>
) : ComponentModel {

//    data class SectionItem(val list: List<EmbeddedListItem>) {
//
//        data class EmbeddedListItem(
//            val template: String,
//            val title: String,
//            val image: String?,
//            val icon: Icon?,
//            val initials: Initials?,
//            val badge: Badge?,
//            val onClick: JSONObject? // This onClick Contains list of buttons which has nested on clicks
//        ) {
//
//            data class Icon(val font: String, val name: String, val color: Color) {
//                companion object {
//                    fun map(jsonObject: JSONObject): Icon {
//                        val font = jsonObject.nullableString("font") ?: ""
//                        val name = jsonObject.nullableString("name") ?: ""
//                        val color = jsonObject.nullableColor("color") ?: Color.Black
//                        return Icon(font = font, name = name, color = color)
//                    }
//                }
//            }
//
//            data class Initials(val text: String, val color: Color, val backgroundColor: Color) {
//                companion object {
//                    fun map(jsonObject: JSONObject): Initials {
//                        val text = jsonObject.nullableString("text") ?: ""
//                        val color = jsonObject.nullableColor("color") ?: Color.White
//                        val backgroundColor =
//                            jsonObject.nullableColor("backgroundColor") ?: Color.Transparent
//                        return Initials(
//                            text = text,
//                            color = color,
//                            backgroundColor = backgroundColor
//                        )
//                    }
//                }
//            }
//
//            data class Badge(val text: String, val color: Color, val backgroundColor: Color) {
//                companion object {
//                    fun map(jsonObject: JSONObject): Badge {
//                        val text = jsonObject.nullableString("text") ?: ""
//                        val color = jsonObject.nullableColor("color") ?: Color.Transparent
//                        val backgroundColor =
//                            jsonObject.nullableColor("backgroundColor") ?: Color.Transparent
//                        return Badge(text = text, color = color, backgroundColor = backgroundColor)
//                    }
//                }
//            }
//
//            companion object {
//                fun map(jsonObject: JSONObject): EmbeddedListItem {
//                    val template = jsonObject.nullableString("template")
//                        ?: jsonObject.nullableString("type") ?: ""
//                    val title = jsonObject.nullableString("title") ?: ""
//                    val image = jsonObject.nullableString("image")
//                    val icon = jsonObject.sliceJsonObject("icon")?.let { Icon.map(it) }
//                    val initials = jsonObject.sliceJsonObject("initials")?.let { Initials.map(it) }
//                    val badge = jsonObject.sliceJsonObject("badge")?.let { Badge.map(it) }
//                    val onClick = jsonObject.sliceJsonObject("onClick")
//                    return EmbeddedListItem(
//                        template = template,
//                        title = title,
//                        image = image,
//                        icon = icon,
//                        initials = initials,
//                        badge = badge,
//                        onClick = onClick
//                    )
//                }
//            }
//        }
//
//        companion object {
//            fun map(jsonObject: JSONObject): SectionItem {
//                val items =
//                    jsonObject.sliceJSONObjectList("items")?.map { EmbeddedListItem.map(it) }
//                return SectionItem(list = items ?: emptyList())
//            }
//        }
//    }

    companion object {
        fun map(jsonObject: JSONObject): ScrollPanelModel {
            val type = jsonObject.nullableString("view") ?: ""
//
//            val format = jsonObject.nullableString("format") ?: ""
//            val isScrollable = format == "scroll"
//
//            val direction = jsonObject.nullableString("direction") ?: ""
//            val height = jsonObject.nullableString("height")?.toIntOrNull() ?: 0
//
//            val aspectRatioString = jsonObject.nullableString("aspectRatio") ?: ""
//            val aspectRatioList = aspectRatioString.split(":")
//            val aspectRatioValue = if (aspectRatioList.isNotEmpty()) {
//                val aspectRatioVertical = aspectRatioList.getOrNull(0)?.toFloatOrNull()
//                val aspectRatioHorizontal = aspectRatioList.getOrNull(1)?.toFloatOrNull()
//                aspectRatioHorizontal?.let { aspectRatioVertical?.div(it) }
//            } else {
//                null
//            }
//
//            val itemSpacing = jsonObject.nullableString("itemSpacing")?.toIntOrNull() ?: 0
//            val horizontalPadding =
//                jsonObject.nullableString("horizontalPadding")?.toIntOrNull() ?: 0
//            val sections = jsonObject.sliceJSONObjectList("sections")?.map { SectionItem.map(it) }
//            val flattenedList = sections?.flatMap { it.list }

            val views = jsonObject.nullableObjectList("components")?.map { getComponentModel(it) }

//            ComponentModel.map(it)
            return ScrollPanelModel(
                type = type,
                childItems = views ?: emptyList()
//                isScrollable = isScrollable,
//                direction = direction,
//                height = height,
//                aspectRatio = aspectRatioValue,
//                itemSpacing = itemSpacing,
//                horizontalPadding = horizontalPadding,
//                listItems = flattenedList ?: emptyList()
            )
        }
    }
}

//// All the embeddedList Items must implement this interface
//interface EmbeddedListItem {
//    val type: String
//}
