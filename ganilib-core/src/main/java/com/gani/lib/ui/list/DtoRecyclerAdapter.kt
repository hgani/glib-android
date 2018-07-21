package com.gani.lib.ui.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.gani.lib.R

abstract class DtoRecyclerAdapter<DO, VH : DtoBindingHolder<DO>> protected constructor(private val objects: List<DO>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    fun initForList(recyclerView: RecyclerView) {
//        initForList(recyclerView, true)
//    }
//
//    fun initForList(recyclerView: RecyclerView, withSeparator: Boolean): RecyclerListHelper {
//        val context = recyclerView.context
//        if (withSeparator) {
//            recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST))
//        }
//        recyclerView.adapter = this
//        return RecyclerListHelper(recyclerView)
//    }

//    fun inflate(parent: ViewGroup, layoutId: Int): ViewGroup {
//        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false) as ViewGroup
//    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    private fun isPositionFooter(position: Int): Boolean {
        return position == itemCount - 1
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionHeader(position)) {
            return R.id.listitem_header
        } else if (isPositionFooter(position)) {
            return R.id.listitem_footer
        }
        return determineViewType(getItem(position - 1))
    }

    // Should return 1 or higher
    protected fun determineViewType(item: DO): Int {
        return R.id.listitem_normal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        if (isPositionHeader(i)) {
            (holder as GenericBindingHolder).update()
        } else if (isPositionFooter(i)) {
            (holder as GenericBindingHolder).update()
        } else {
            (holder as VH).update(getItem(i - 1))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Need to use `if` instead of `switch`. See http://stackoverflow.com/questions/8476912/menu-item-ids-in-an-android-library-project
        return if (viewType == R.id.listitem_header) {
            onCreateHeaderHolder(parent)
        } else if (viewType == R.id.listitem_footer) {
            onCreateFooterHolder(parent)
        } else {
            onCreateItemHolder(parent, viewType)
        }
    }

    protected abstract fun onCreateItemHolder(parent: ViewGroup, viewType: Int): VH

    protected fun onCreateHeaderHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return BlankGenericItemHolder(parent.context)
    }

    protected fun onCreateFooterHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return BlankGenericItemHolder(parent.context)
    }

    override fun getItemCount(): Int {
        return objects.size + 2  // Header and footer
    }

    //  /**
    //   * @see android.widget.ListAdapter#getItemId(int)
    //   */
    //  @Override
    //  public long getItemId(int position) {
    ////    if (mDataValid && mCursor != null) {
    ////      if (mCursor.moveToPosition(position)) {
    ////        return mCursor.getLong(mRowIDColumn);
    ////      } else {
    ////        return 0;
    ////      }
    ////    } else {
    ////      return 0;
    ////    }
    //    return objects.get(position);
    //  }

    fun getItem(position: Int): DO {
        return objects[position]
    }


    abstract class GenericBindingHolder(context: Context, selectable: Boolean) : AbstractBindingHolder(context, selectable) {
        abstract fun update()
    }

    class BlankGenericItemHolder(context: Context) : GenericBindingHolder(context, false) {
        override fun update() {

        }
    }
}