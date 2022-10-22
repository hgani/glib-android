package com.gani.lib.select

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.glib.core.R
import com.glib.core.screen.GActivity
import com.glib.core.screen.GScreen
import com.glib.core.ui.select.SelectableItem
import com.glib.core.utils.Res
import java.io.Serializable
import java.util.*

class ItemSelectScreenHelper<I : SelectableItem?> {
    private var activity: GScreen? = null
    private var fragment: FragmentItemSelect<I?>? = null
    var mutableSelectedItems: MutableSet<I?>
        private set
    private var multiselect: Boolean

    constructor(
        activity: GScreen, savedInstanceState: Bundle?, fragment: FragmentItemSelect<I?>?,
        multiselect: Boolean
    ) {
        this.activity = activity
        this.fragment = fragment
        this.multiselect = multiselect
        mutableSelectedItems = if (savedInstanceState == null) LinkedHashSet(
            activity.getIntent()
                .getSerializableExtra(PARAM_SELECTED_ITEMS) as MutableList<I?>
        ) else  //        new LinkedHashSet<I>((List<I>) initialSelection) :
            savedInstanceState.getSerializable(BUNDLE_SELECTED_ITEMS) as LinkedHashSet<I?>

//    onCreate(savedInstanceState);
        activity.setFragmentWithToolbar(fragment, savedInstanceState)
    }

    constructor(savedInstanceState: Bundle?, multiselect: Boolean) {
        this.multiselect = multiselect
        mutableSelectedItems =
            savedInstanceState!!.getSerializable(BUNDLE_SELECTED_ITEMS) as LinkedHashSet<I?>
    }

    //  protected ItemSelectScreenHelper(FragmentItemSelect<I, T> fragment) {
    //    this.fragment = fragment;
    //  }
    //
    //  private void onCreate(Bundle savedInstanceState) {
    //    activity.setFragmentWithToolbar(fragment, false, savedInstanceState);
    //  }
    fun initResult() {
        activity?.setOkResult(
            FragmentItemSelect.RETURN_ITEMS,
            ArrayList(mutableSelectedItems)
        )
    }

    fun onSaveInstanceState(outState: Bundle?) {
//    super.onSaveInstanceState(outState);
        outState!!.putSerializable(
            BUNDLE_SELECTED_ITEMS,
            mutableSelectedItems as Serializable?
        )
    }

    fun findViewById(id: Int): View? {
        return activity?.findViewById(id)
    }

    internal inner class ActivityNotifier(private val item: I?) :
        CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(
            buttonView: CompoundButton?,
            isChecked: Boolean
        ) {
            if (isChecked) {
                if (!multiselect) {
                    val listView: RecyclerView =
                        activity?.findViewById(R.id.list_common) as RecyclerView
                    for (i in 0 until listView.getChildCount()) {
                        val itemView: View = listView.getChildAt(i)
                        val selectButton =
                            itemView.findViewById<View?>(R.id.toggle_select) as CheckBox?
                        // selectButton may be null if this is a section header
                        if (selectButton != null && selectButton !== buttonView) {
                            selectButton.isChecked = false
                        }
                    }
                    mutableSelectedItems.clear()
                }
                mutableSelectedItems.add(item)
            } else {
                mutableSelectedItems.remove(item)
            }
        }

    }

    companion object {
        val PARAM_SELECTED_ITEMS: String = "selectedItems"
        private val BUNDLE_SELECTED_ITEMS: String = "selectedItems"

        //  public static final String RETURN_ITEMS = "items";
        fun <I : SelectableItem?> intent(
            cls: Class<out GActivity?>?,
            currentSelection: List<I>
        ): Intent {
            val intent = Intent(Res.context, cls)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            intent.putExtra(
                PARAM_SELECTED_ITEMS,
                currentSelection as Serializable?
            )
            //    intent.putExtra(FragmentItemSelect.PARAM_MULTISELECT, multiselect);
            return intent
        }
    }
}