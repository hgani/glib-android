package com.glib.core.ui.list.templates

import android.content.Context
import android.text.TextUtils
import com.glib.core.ui.list.DtoBindingHolder
import com.glib.core.ui.view.GTextView

abstract class TextHolder<DO>(context: Context, selectable: Boolean) : DtoBindingHolder<DO>(context, selectable) {
    val title = GTextView(context).specs(GTextView.Spec.cellTitle)
    val subtitle = GTextView(context).specs(GTextView.Spec.cellSubtitle).maxLines(1).ellipsize(TextUtils.TruncateAt.END)

    init {
        container
                .padding(10, 10, 10, 10)
                .append(title)
                .append(subtitle)
    }
}