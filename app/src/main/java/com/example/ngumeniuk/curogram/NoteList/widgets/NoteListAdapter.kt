package com.example.ngumeniuk.curogram.NoteList.widgets

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ngumeniuk.curogram.R
import com.example.ngumeniuk.curogram.data.models.NoteModel
import kotlinx.android.synthetic.main.item_note_list_view.view.*


class NoteListAdapter(val list: MutableList<NoteModel>) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_note_list_view))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(list[position])

    private fun ViewGroup.inflate(layoutRes: Int): View =
            LayoutInflater.from(context).inflate(layoutRes, this, false)

    override fun getItemCount(): Int = list.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: NoteModel) {
            with(itemView) {
                with(model) {
                    titleTV.text = title
                    textTV.text = text
                    dateTV.text = date
                }
            }
        }
    }
}
