package com.example.ngumeniuk.curogram.noteList.widgets

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ngumeniuk.curogram.R
import com.example.ngumeniuk.curogram.data.models.NoteModel
import com.example.ngumeniuk.curogram.utils.DateUtil
import com.example.ngumeniuk.curogram.widgets.ListAdapter
import kotlinx.android.synthetic.main.item_note_list_view.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class NoteListAdapter(private val function: (model: NoteModel) -> Unit)
    : ListAdapter<NoteModel, NoteListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_note_list_view))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(list[position], function)

    private fun ViewGroup.inflate(layoutRes: Int): View =
            LayoutInflater.from(context).inflate(layoutRes, this, false)


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: NoteModel, func: (model: NoteModel) -> Unit) {
            with(itemView) {
                with(model) {
                    titleTV.text = title
                    textTV.text = text
                    dateTV.text = DateUtil.formatedDate(date)
                }
            }
            itemView.onClick { func(model) }
        }
    }
}
