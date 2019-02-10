package com.gani.lib.ui.list.templates

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import com.gani.lib.ui.list.DtoBindingHolder
import com.gani.lib.ui.panel.GHorizontalPanel
import com.gani.lib.ui.panel.GVerticalPanel
import com.gani.lib.ui.view.GImageView
import com.gani.lib.ui.view.GTextView

abstract class ThumbnailHolder<DO>(context: Context, selectable: Boolean) : DtoBindingHolder<DO>(context, selectable) {
    protected val image = GImageView(context).width(100).height(100)
    protected val title = GTextView(context).specs(GTextView.Spec.cellTitle)
    protected val subtitle = GTextView(context).specs(GTextView.Spec.cellSubtitle).maxLines(1).ellipsize(TextUtils.TruncateAt.END)

    init {
        container
                .padding(10, 10, 10, 10)
                .append(GHorizontalPanel(context).append(image)
                            .append(GVerticalPanel(context).padding(10, 10, 10, 10).append(title).append(subtitle))
                )
    }

    fun setImageUrl(url: String?) {
        if (url == null) {
            image.visibility = View.GONE
        } else {
            image.source(url).visibility = View.VISIBLE
        }
    }

    fun setTitle(text: String?) {
        if (text == null) {
            title.visibility = View.GONE
        } else {
            title.text(text).visibility = View.VISIBLE
        }
    }

    fun setSubtitle(text: String?) {
        if (text == null) {
            subtitle.visibility = View.GONE
        } else {
            subtitle.text(text).visibility = View.VISIBLE
        }
    }
}