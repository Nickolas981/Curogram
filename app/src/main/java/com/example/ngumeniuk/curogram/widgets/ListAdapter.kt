package com.example.ngumeniuk.curogram.widgets

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import com.example.ngumeniuk.curogram.utils.Differeble
import com.example.ngumeniuk.curogram.utils.NoteDiffUtilCallback


abstract class ListAdapter<T : Differeble, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    val list: MutableList<T> = ArrayList()

    override fun getItemCount(): Int = list.size

    override fun getItemId(i: Int): Long = i.toLong()

    fun change(items: List<T>) {
        val diff = DiffUtil.calculateDiff(NoteDiffUtilCallback(list, items.toList()))
        clearList()
        list.addAll(items)
        diff.dispatchUpdatesTo(this)
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun clearList() {
        list.clear()
    }
}