package com.glib.core.compose.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.glib.core.compose.core.extensions.objectList
import com.glib.core.compose.core.extensions.string
import com.glib.core.logging.GLog
import org.json.JSONObject

@Composable
fun ResponsivePanel(model: JSONObject, navigateTo: (JSONObject) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize(), state = rememberLazyListState(), contentPadding = PaddingValues(bottom = 14.dp)) {
        items(items = model.objectList("childViews")) { model ->
            ComponentView(componentModel = model, navigateTo = navigateTo)
        }
    }
}




@Composable
fun ComponentView(
    componentModel: JSONObject,
    navigateTo: (JSONObject) -> Unit
) {
    val type = componentModel.string("view")
    return when (type.removeSuffix("-v1")) {
        "panels/scroll" -> {
            ScrollPanel(model = componentModel, navigateTo = navigateTo)
        }
        "h1" -> {
            H1(model = componentModel, navigateTo = navigateTo)
        }
        "h2" -> {
            H2(model = componentModel, navigateTo = navigateTo)
        }
        else -> {
            // From: https://stackoverflow.com/questions/72278954/get-composable-function-name-inside-it
            GLog.e(object{}::class.java, "Invalid view type: ${type}")
            EmptyComponent(model = componentModel)
        }
    }
}
