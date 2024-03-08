package com.glib.core.compose.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glib.core.compose.core.extensions.getTypography
import com.glib.core.compose.core.extensions.string
import org.json.JSONObject

@Composable
fun H1(model: JSONObject, navigateTo: ((JSONObject) -> Unit)? = null) {
    Text(
        model.string("text"),
//        style = TextStyle(
//            fontSize = 30.sp,
//            fontWeight = FontWeight.Bold
//        )
        style = "h1".getTypography()
    )
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
