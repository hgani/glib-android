package com.glib.core.compose.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.glib.core.compose.components.ComponentView
import com.glib.core.compose.core.extensions.objectList
import org.json.JSONObject

@Composable
fun ResponsivePanel(model: JSONObject, navigateTo: (JSONObject) -> Unit) {
    Column {
        model.objectList("childViews").forEach {
            ComponentView(componentModel = it, navigateTo = navigateTo)
        }
    }
}
