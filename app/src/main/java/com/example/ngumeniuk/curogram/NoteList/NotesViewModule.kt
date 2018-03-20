package com.example.ngumeniuk.curogram.NoteList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.ngumeniuk.curogram.data.dataRepositories.NotesRepository
import com.example.ngumeniuk.curogram.data.models.NoteModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.coroutines.experimental.bg
import java.util.*


class NotesViewModule : ViewModel() {

    private val dataRep by lazy { NotesRepository() }

    private val liveDataList by lazy { MutableLiveData<List<NoteModel>>() }


    fun loadNotes() {
        if (liveDataList.value == null)
            getAllNotes()
    }

    private fun getAllNotes() {
        dataRep.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setLiveData)
    }

    private fun setLiveData(data: List<NoteModel>) {
        liveDataList.value = data
    }

    fun getNotesLiveData(): LiveData<List<NoteModel>> = liveDataList

    fun delete(model: NoteModel) {
        bg { dataRep.delete(model) }
    }

    fun putDummy() {
        bg {
            dataRep.putNote(NoteModel("Title", "Text", Calendar.getInstance().timeInMillis))
        }
    }

    fun drop() {
        bg {
            dataRep.dropTable()
        }
    }
}
