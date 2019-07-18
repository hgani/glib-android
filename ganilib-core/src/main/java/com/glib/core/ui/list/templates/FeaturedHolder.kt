package com.glib.core.ui.list.templates

import android.content.Context
import android.text.TextUtils
import android.view.ViewGroup
import com.glib.core.ui.list.DtoBindingHolder
import com.glib.core.ui.panel.GVerticalPanel
import com.glib.core.ui.view.GImageView
import com.glib.core.ui.view.GTextView

abstract class FeaturedHolder<DO>(context: Context, selectable: Boolean) : DtoBindingHolder<DO>(context, selectable) {
    val image = GImageView(context).width(ViewGroup.LayoutParams.MATCH_PARENT).height(210).centerCrop()
    val title = GTextView(context).specs(GTextView.Spec.cellTitle)
    val subtitle = GTextView(context).specs(GTextView.Spec.cellSubtitle).maxLines(1).ellipsize(TextUtils.TruncateAt.END)

    init {
        container.padding(10, 10, 10, 10)
                .append(image)
                .append(GVerticalPanel(context).padding(10, 5, 10, 10).append(title).append(subtitle))
    }
}