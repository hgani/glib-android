package com.glib.core.compose.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.glib.core.compose.components.ComponentView
import com.glib.core.compose.core.extensions.objectList
import org.json.JSONObject

@Composable
fun ScrollPanelView(model: JSONObject, navigateTo: (JSONObject) -> Unit) {
    Column {
        model.objectList("childViews").forEach {
            ComponentView(componentModel = it, navigateTo = navigateTo)
        }
    }

//    when (embeddedListModel.direction) {
//        "horizontal" -> {
//            LazyRow(
//                contentPadding = PaddingValues(horizontal = embeddedListModel.horizontalPadding.dp),
//                modifier = Modifier.height(embeddedListModel.height.dp), // usually we consider backend value in px and convert to dp but this on is exception
//                horizontalArrangement = Arrangement.spacedBy(
//                    embeddedListModel.itemSpacing.dp // usually we consider backend value in px and convert to dp but this on is exception
//                ),
//                userScrollEnabled = embeddedListModel.isScrollable
//            ) {
//                items(embeddedListModel.listItems) {
//                    when (it.template) {
//                        "basicIconImageCard" -> {
//                            EmbeddedlogListBasicItemCard(
//                                sectionItems = it,
//                                embeddedListModel.aspectRatio,
//                                navigateTo
//                            )
//                        }
//
//                        else -> {}
//                    }
//                }
//            }
//        }
//
//        // If outer Lazy list in same direction then this will crash.
//        "vertical" -> {
//            LazyColumn(
//                modifier = Modifier.height(embeddedListModel.height.dp),
//                verticalArrangement = Arrangement.spacedBy(
//                    embeddedListModel.itemSpacing.dp // usually we consider backend value in px and convert to dp but this on is exception
//                ),
//                userScrollEnabled = embeddedListModel.isScrollable
//            ) {
//                items(embeddedListModel.listItems) {
//                    when (it.template) {
//                        "basicIconImageCard" -> {
//                            EmbeddedListBasicItemCard(
//                                sectionItems = it,
//                                embeddedListModel.aspectRatio,
//                                navigateTo = navigateTo
//                            )
//                        }
//
//                        else -> {}
//                    }
//                }
//            }
//        }
//    }
}

//@Preview
//fun ScrollPanelViewPreview() {
//    ScrollPanelView(ScrollPanelModel())
//}
