package com.glib.core.ui.style

import com.glib.core.ui.icon.GIcon
import com.glib.core.ui.icon.GIconHelper
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial

enum class LibIcon private constructor(delegate: IIcon) : GIcon {
    icon_menu(GoogleMaterial.Icon.gmd_menu),
    icon_back(GoogleMaterial.Icon.gmd_arrow_back);

    private val helper: GIconHelper

    init {
        this.helper = GIconHelper(delegate)
    }

    override fun drawable(): IconicsDrawable {
        return helper.drawable()
    }
}