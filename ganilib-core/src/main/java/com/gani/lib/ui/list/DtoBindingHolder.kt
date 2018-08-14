package com.gani.lib.ui.list

import android.content.Context

abstract class DtoBindingHolder<DO> : AbstractBindingHolder {
//    constructor(parent: ViewGroup, layoutId: Int, selectable: Boolean) : super(parent, layoutId, selectable) {}

    constructor(context: Context, selectable: Boolean) : super(context, selectable) {}

    abstract fun update(item: DO)
}