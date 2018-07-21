package com.gani.lib.ui.icon

import com.gani.lib.utils.Res
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon

class GIconHelper(private val delegate: IIcon) : GIcon {
    override fun drawable(): IconicsDrawable {
        return IconicsDrawable(Res.context).icon(delegate)
    }
}
