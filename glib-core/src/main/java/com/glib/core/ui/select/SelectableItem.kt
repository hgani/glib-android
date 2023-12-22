package com.glib.core.ui.select

import android.view.View
import android.widget.TextView
import com.glib.core.utils.string.StringUtils.join
import java.io.Serializable
import java.util.*

interface SelectableItem : Serializable {
    val name: String

    object Utils {
        private fun collectNames(items: List<SelectableItem>): List<String> {
            val itemNames: MutableList<String> =
                LinkedList()
            for (item in items) {
                itemNames.add(item.name)
            }
            return itemNames
        }

        fun toLineSeparatedNames(items: List<SelectableItem>): String {
            return join(
                collectNames(
                    items
                ), "\n"
            )!!
        }

        fun toSpaceSeparatedNames(items: List<SelectableItem>): String {
            return join(
                collectNames(
                    items
                ), " "
            )!!
        }

        // Set to empty when there is no error. This is important for determining form validity (e.g. when enabling submit button).
        fun initLayout(
            selected: TextView,
            error: TextView,
            items: List<SelectableItem>
        ) {
            val names =
                toLineSeparatedNames(items)
            selected.text = names
            selected.visibility = if (names.isEmpty()) View.GONE else View.VISIBLE
            if (names.isEmpty()) {
//        error.setText(R.string.error_empty);
                error.text = "Cannot be empty"
            } else {
                error.text = ""
            }
        }
    }
}