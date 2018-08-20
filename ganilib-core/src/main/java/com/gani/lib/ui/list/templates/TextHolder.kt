package com.gani.lib.ui.list.templates

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.ViewGroup
import com.gani.lib.ui.list.DtoBindingHolder
import com.gani.lib.ui.view.GTextView

abstract class TextHolder<DO>(context: Context, selectable: Boolean) : DtoBindingHolder<DO>(context, selectable) {
    val title = GTextView(context).spec(GTextView.Spec.cellTitle)
    val subtitle = GTextView(context).spec(GTextView.Spec.cellSubtitle).maxLines(1).ellipsize(TextUtils.TruncateAt.END)

    init {
        container
                .padding(10, 10, 10, 10)
                .append(title)
                .append(subtitle)
    }
}