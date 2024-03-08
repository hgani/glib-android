package com.glib.core.compose.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

fun android.graphics.Color.parseColor(colorString: String): Int {
    return when(colorString) {
        "transparent" -> android.graphics.Color.TRANSPARENT
        "warning" -> android.graphics.Color.argb(1, 251, 140, 0)
        "success" -> android.graphics.Color.argb(1, 76, 175, 80)
        else -> {
            try {
                return android.graphics.Color.parseColor(colorString)
            } catch(e: IllegalArgumentException) {
                android.graphics.Color.WHITE // Default
            }
        }
    }
}
