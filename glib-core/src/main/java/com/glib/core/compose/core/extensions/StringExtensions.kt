package com.glib.core.compose.core.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.glib.core.compose.theme.Typography

/**
 * @return typography based on the string
 */
fun String.getTypography(): TextStyle {
    return when(this) {
        "h1" -> Typography.headlineLarge
        "h2" -> Typography.headlineMedium
        "h3" -> Typography.headlineSmall
        else -> Typography.bodyMedium // default
    }
}

// TODO
///**
// * @sample "fa-duotone".getFontFamily()
// * @return FontFamily (Default: FontFamily.Default)
// */
//fun String.getFontFamily(): FontFamily {
//    return when(this) {
//        "fa-brands" -> FontFamily(Font(R.font.font_awsome_6_brands_regular_400))
//        "fa-duotone" -> FontFamily(Font(R.font.font_awsome_6_duotone_solid_900))
//        "fa-light" -> FontFamily(Font(R.font.font_awesome_6_pro_light_300))
//        "fa-regular" -> FontFamily(Font(R.font.font_awesome_6_pro_regular_400))
//        "fa-solid" -> FontFamily(Font(R.font.font_awesome_6_pro_solid_900))
//        "fa-thin" -> FontFamily(Font(R.font.font_awesome_6_pro_thin_100))
//        else -> FontFamily.Default
//    }
//}

/**
 * @return return Color if string is valid otherwise null
 */
fun String.parseColor(): Color? {
    return try {
        Color(android.graphics.Color.parseColor(this))
    } catch (e: IllegalArgumentException) {
        // TODO
//        FirebaseCrashlytics.getInstance().recordException(e)
        e.printStackTrace()
        return null
    }
}