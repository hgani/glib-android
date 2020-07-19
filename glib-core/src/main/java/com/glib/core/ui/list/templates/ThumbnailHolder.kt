package com.glib.core.ui.list.templates

import android.content.Context
import android.text.TextUtils
import android.view.View
import com.glib.core.ui.list.DtoBindingHolder
import com.glib.core.ui.panel.GHorizontalPanel
import com.glib.core.ui.panel.GVerticalPanel
import com.glib.core.ui.view.GImageView
import com.glib.core.ui.view.GTextView
import com.glib.core.ui.view.GView

abstract class ThumbnailHolder<DO>(context: Context, selectable: Boolean) : DtoBindingHolder<DO>(context, selectable) {
    protected val image = GImageView(context).width(100).height(100)
    protected val title = GTextView(context).specs(GTextView.Spec.cellTitle)
    protected val subtitle = GTextView(context).specs(GTextView.Spec.cellSubtitle).maxLines(1).ellipsize(TextUtils.TruncateAt.END)
    protected val subsubtitle = GTextView(context).specs(GTextView.Spec.cellSubsubtitle).maxLines(1).ellipsize(TextUtils.TruncateAt.END)

    init {
        container
                .padding(10, 10, 10, 10)
                .append(GHorizontalPanel(context).append(image)
                            .append(
                                    GVerticalPanel(context).padding(10, 10, 10, 10)
                                            .append(title)
                                            .append(subtitle)
                                            .append(GView(context).height(4))
                                            .append(subsubtitle)
                            )
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

    fun setSubsubtitle(text: String?) {
        if (text == null) {
            subsubtitle.visibility = View.GONE
        } else {
            subsubtitle.text(text).visibility = View.VISIBLE
        }
    }
}