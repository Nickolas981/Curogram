package com.example.ngumeniuk.curogram.utils

import android.support.v7.util.DiffUtil


class NoteDiffUtilCallback<T : Differeble>(val oldList: List<T>, val newList: List<T>)
    : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].getIdToDiff() == newList[newItemPosition].getIdToDiff()

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
}