package com.glib.core.compose.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.glib.core.compose.core.extensions.string
import org.json.JSONObject

@Composable
fun H2(model: JSONObject, navigateTo: ((JSONObject) -> Unit)? = null) {
    Text(
        model.string("text"),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    )
}


@Composable
@Preview
fun H2Preview() {
    val model = JSONObject("""
        {
            text: "Heading #2"
        }
    """.trimIndent())
    H2(model = model)
}
