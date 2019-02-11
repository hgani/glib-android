package com.gani.lib.ui.icon

import com.mikepenz.iconics.IconicsDrawable

// NOTE: Call the following line when proguard or applicationId mismatch prevents the auto detection of the fonts.
//       From https://github.com/mikepenz/Android-Iconics/issues/234
// Iconics.registerFont(GoogleMaterial())
interface GIcon {
    fun drawable(): IconicsDrawable

    companion object {
        val ACTION_BAR_SIZE = 20
    }
}
