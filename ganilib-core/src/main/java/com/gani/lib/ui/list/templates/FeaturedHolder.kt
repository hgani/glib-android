package com.gani.lib.ui.list.templates

import android.content.Context
import android.text.TextUtils
import com.gani.lib.ui.list.DtoBindingHolder
import com.gani.lib.ui.panel.GVerticalPanel
import com.gani.lib.ui.view.GImageView
import com.gani.lib.ui.view.GTextView

abstract class FeaturedHolder<DO>(context: Context, selectable: Boolean) : DtoBindingHolder<DO>(context, selectable) {
    val image = GImageView(context).height(200).centerCrop()
    val title = GTextView(context).spec(GTextView.Spec.cellTitle)
    val subtitle = GTextView(context).spec(GTextView.Spec.cellSubtitle).maxLines(1).ellipsize(TextUtils.TruncateAt.END)

    init {
        container.padding(10, 10, 10, 10)
                .append(image)
                .append(GVerticalPanel(context).padding(10, 5, 10, 10).append(title).append(subtitle))
    }
}