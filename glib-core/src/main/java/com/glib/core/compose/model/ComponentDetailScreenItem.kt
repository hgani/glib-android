package com.glib.core.compose.model

import androidx.compose.ui.graphics.Color
import com.glib.core.compose.components.EmptyModel
import com.glib.core.compose.core.extensions.nullableColor
import com.glib.core.compose.core.extensions.nullableString
import com.glib.core.compose.core.extensions.sliceJSONObjectList
import org.json.JSONObject

// We're avoiding mapping of "component" as it is being mapped and handled in relevant class
// We're avoiding mapping of "navMenu", "onLoad", "onFocus" for now, once we needed we can map them
data class ComponentDetailScreenItem(
    val section: String,
    val title: String,
    val contentTitle: String,
    val webUrl: String,
    val barColor: Color,
    val navTintColor: Color,
    val id: Int,
    val cacheKey: String,
    val scrollableNavBar: Boolean,
    val reloadWindowOnFocus: Boolean,
    val toolbarType: String,
//    val profileHeaderV2Model: ProfileHeaderV2Model?,
    val components: List<ComponentModel>?
) {

    companion object {
        fun map(jsonObject: JSONObject): ComponentDetailScreenItem {
            val section = jsonObject.nullableString("section") ?: ""
            val title = jsonObject.nullableString("title") ?: ""
            val contentTitle = jsonObject.nullableString("contentTitle") ?: ""
            val webUrl = jsonObject.nullableString("webUrl") ?: ""
            val barColor = jsonObject.nullableColor("barColor") ?: Color.Transparent
            val navTintColor = jsonObject.nullableColor("navTintColor") ?: Color.Transparent
            val id = jsonObject.nullableString("id")?.toInt() ?: 0
            val cacheKey = jsonObject.nullableString("cacheKey") ?: ""
            val scrollableNavBar =
                jsonObject.nullableString("scrollableNavBar")?.toBoolean() ?: false
            val reloadWindowOnFocus =
                jsonObject.nullableString("reloadWindowOnFocus")?.toBoolean() ?: false
            val toolbarType = jsonObject.nullableString("toolbarType") ?: ""

//            val profileHeaderV2Model = jsonObject.sliceJsonObject("profileHeaderV2")?.let { ProfileHeaderV2Model.map(it) }
            val components = jsonObject.sliceJSONObjectList("components")?.map { getComponentModel(it) }

            return ComponentDetailScreenItem(
                section = section,
                title = title,
                contentTitle = contentTitle,
                webUrl = webUrl,
                barColor = barColor,
                navTintColor = navTintColor,
                id = id,
                cacheKey = cacheKey,
                scrollableNavBar = scrollableNavBar,
                reloadWindowOnFocus = reloadWindowOnFocus,
                toolbarType = toolbarType,
//                profileHeaderV2Model = profileHeaderV2Model,
                components = components
            )
        }
    }
}

private fun getComponentModel(jsonObject: JSONObject): ComponentModel {
    return EmptyModel.map(jsonObject)

//    val type = jsonObject.nullableString("type")
//    return when (type) {
        // TODO
//        "profileHeaderV2" -> {
//            ProfileHeaderV2Model.map(jsonObject)
//        }
//
//        "spacer" -> {
//            SpacerModel.map(jsonObject)
//        }
//
//        "separator" -> {
//            SeparatorModel.map(jsonObject)
//        }
//
//        "sectionTitle" -> {
//            SectionTitleModel.map(jsonObject)
//        }
//
//        "chipGroup" -> {
//            ChipGroupModel.map(jsonObject)
//        }
//
//        "callToAction" -> {
//            CallToActionModel.map(jsonObject)
//        }
//
//        "fieldLabel" -> {
//            FieldLabelModel.map(jsonObject)
//        }
//
//        "accordionItemTop" -> {
//            AccordionItemModel.map(jsonObject)
//        }
//
//        "accordionItemCenter" -> {
//            AccordionItemModel.map(jsonObject)
//        }
//
//        "accordionItemBottom" -> {
//            AccordionItemModel.map(jsonObject)
//        }
//
//        "embeddedList" -> {
//            EmbeddedListModel.map(jsonObject)
//        }
//
//        else -> {
//        EmptyModel.map(jsonObject)
//        }
//    }
}
