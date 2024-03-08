package com.glib.core.compose.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.glib.core.compose.core.extensions.nullableString
import com.glib.core.compose.core.extensions.string
import com.glib.core.compose.model.ScrollPanelModel
import com.glib.core.compose.model.TextModel
import org.json.JSONObject

@Composable
fun H1View(model: JSONObject, navigateTo: (JSONObject) -> Unit) {
    Text(model.string("text"))

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
//                            EmbeddedListBasicItemCard(
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
