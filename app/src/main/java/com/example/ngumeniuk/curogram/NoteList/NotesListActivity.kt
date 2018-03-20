package com.example.ngumeniuk.curogram.NoteList

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.ngumeniuk.curogram.NoteList.widgets.NoteListAdapter
import com.example.ngumeniuk.curogram.R
import com.example.ngumeniuk.curogram.ui.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_notes_list.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class NotesListActivity : AppCompatActivity() {

    private val viewModel
            by lazy { ViewModelProviders.of(this).get(NotesViewModule::class.java) }
    private lateinit var adapter: NoteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        viewModel.loadNotes()
        adapter = NoteListAdapter()
        observeToNoteViewModel()
        setOnClick()
        setSwipeHandler()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setSwipeHandler() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.delete(adapter.list[position])
                adapter.removeAt(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setOnClick() {
        addButton.onClick {
            viewModel.putDummy()
        }
    }

    private fun observeToNoteViewModel() {
        viewModel.getNotesLiveData().observe(this, Observer { res ->
            adapter.change(res!!.toList())
            recyclerView.scrollToPosition(0)
        })
    }

}
