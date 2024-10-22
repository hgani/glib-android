package com.glib.core.ui.select

import android.content.Context
import android.widget.CheckBox
import com.gani.lib.select.ItemSelectScreenHelper
import com.glib.core.R
import com.glib.core.ui.list.DtoBindingHolder

abstract class DtoItemSelectHolder<I : SelectableItem?> protected constructor(
    context: Context?,
    private val helper: ItemSelectScreenHelper<I>,
    multiselect: Boolean
) : DtoBindingHolder<I>(
    context!!, false
) {
    //  private boolean multiselect;
    private val selectButton: CheckBox

    init {
        //    this.multiselect = multiselect;
        selectButton = getLayout().findViewById(R.id.toggle_select)

        // TODO
//        this.selectButton = itemView.findViewById(R.id.toggle_select);
    }

    override fun update(item: I) {
        selectButton.isChecked = helper.mutableSelectedItems.contains(item)
        selectButton.setOnCheckedChangeListener(helper.ActivityNotifier(item))
    } //  @Override
    //  public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
    //    if (view.getId() == R.id.toggle_select) {
    //      CheckBox selectButton = (CheckBox) view;
    //      selectButton.setOnCheckedChangeListener(null); // Null out listener in case we're reusing a view that possesses a previously put listener
    //
    //      I item = createSelectableItem(cursor);
    //
    //      selectButton.setChecked(helper.getMutableSelectedItems().contains(item));
    //      selectButton.setOnCheckedChangeListener(new ActivityNotifier(item));
    //
    //      return true;
    //    }
    //
    //    return false;
    //  }
}
