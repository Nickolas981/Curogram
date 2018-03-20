package com.example.ngumeniuk.curogram.widgets

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import com.example.ngumeniuk.curogram.utils.NoteDiffUtilCallback


abstract class ListAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    val list: MutableList<T> = ArrayList()

    override fun getItemCount(): Int = list.size

    override fun getItemId(i: Int): Long = i.toLong()

    fun change(vararg items: T) =
            change(items.toList())


    fun change(items: List<T>) {
        val diff = DiffUtil.calculateDiff(NoteDiffUtilCallback(list, items.toList()))
        clearList(false)
        list.addAll(items)
        diff.dispatchUpdatesTo(this)
    }

    fun add(vararg items: T) =
            add(items.toList())


    fun add(items: List<T>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearList(notify: Boolean = true) {
        list.clear()
        if (notify) notifyDataSetChanged()
    }
}