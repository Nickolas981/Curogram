package com.example.ngumeniuk.curogram.noteList

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.example.ngumeniuk.curogram.R
import com.example.ngumeniuk.curogram.addEditNote.AddEditActivity
import com.example.ngumeniuk.curogram.data.models.NoteModel
import com.example.ngumeniuk.curogram.noteList.widgets.NoteListAdapter
import com.example.ngumeniuk.curogram.ui.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_notes_list.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class NotesListActivity : AppCompatActivity() {

    private val ADD_EDIT_REQUEST = 12

    private val notesViewModule
            by lazy { ViewModelProviders.of(this).get(NotesViewModule::class.java) }


    private lateinit var adapter: NoteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        notesViewModule.loadNotes()
        adapter = NoteListAdapter(this::onNoteClick)
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
                notesViewModule.delete(adapter.list[position])
                adapter.removeAt(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_EDIT_REQUEST && resultCode == Activity.RESULT_OK) {
            with(data!!.extras) {
                notesViewModule.addOrUpdate(getInt("id"), getString("title"), getString("text"))
            }
        }
    }

    private fun onNoteClick(model: NoteModel) {
        with(model) {
            startActivityForResult<AddEditActivity>(ADD_EDIT_REQUEST,
                    "text" to text, "title" to title, "id" to id)
        }
    }

    private fun setOnClick() {
        addButton.onClick {
            startActivityForResult<AddEditActivity>(ADD_EDIT_REQUEST, "id" to -1)
        }
    }

    private fun observeToNoteViewModel() {
        notesViewModule.getNotesLiveData().observe(this, Observer { res ->
            adapter.change(res!!.toList())
            recyclerView.scrollToPosition(0)
        })

        notesViewModule.getLoadingLiveData().observe(this, Observer { loading ->
            if (loading!!) {
                loadingView.visibility = View.VISIBLE
                addButton.hide()
            } else {
                loadingView.visibility = View.GONE
                addButton.show()
            }
        })

        notesViewModule.getErrorLiveData().observe(this, Observer { error ->
            if (error!!) {
                alert("This is not me. Eugene asked to make this Error.", "Uppps...Error") {
                    okButton { toast("Really sorry...Try again") }
                }.show()
            }
        })
    }

}
