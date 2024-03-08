package com.glib.core.compose.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.glib.core.compose.core.extensions.string
import org.json.JSONObject

@Composable
fun H1(model: JSONObject, navigateTo: ((JSONObject) -> Unit)? = null) {
    Text(model.string("text"))
}


@Composable
@Preview
fun H1Preview() {
    val model = JSONObject("""
        {
            text: "Heading #1"
        }
    """.trimIndent())
    H1(model = model)
}
