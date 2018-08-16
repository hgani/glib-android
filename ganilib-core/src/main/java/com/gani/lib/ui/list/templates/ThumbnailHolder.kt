package com.gani.lib.ui.list.templates

import android.content.Context
import android.text.TextUtils
import com.gani.lib.ui.layout.GLinearLayout
import com.gani.lib.ui.list.DtoBindingHolder
import com.gani.lib.ui.view.GImageView
import com.gani.lib.ui.view.GTextView

abstract class ThumbnailHolder<DO>(context: Context, selectable: Boolean) : DtoBindingHolder<DO>(context, selectable) {
    val image = GImageView(context).height(100).width(100)
    val name = GTextView(context).spec(GTextView.Spec.cellTitle)
    val description = GTextView(context).spec(GTextView.Spec.cellSubtitle).maxLines(1).ellipsize(TextUtils.TruncateAt.END)

    init {
        container
                .paddings(10, 10, 10, 10)
                .append(GLinearLayout(context).horizontal().append(image)
                        .append(GLinearLayout(context).vertical().paddings(10, 10, 10, 10).append(name).append(description))
                )
    }
}