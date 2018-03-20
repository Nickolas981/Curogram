package com.example.ngumeniuk.curogram.noteList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.ngumeniuk.curogram.data.dataRepositories.NotesRepository
import com.example.ngumeniuk.curogram.data.models.NoteModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import org.jetbrains.anko.coroutines.experimental.bg
import java.util.*


class NotesViewModule : ViewModel() {

    private val dataRep by lazy { NotesRepository() }

    private val liveDataList by lazy { MutableLiveData<List<NoteModel>>() }
    private val liveDataLoading by lazy { MutableLiveData<Boolean>() }
    private val liveDataError by lazy { MutableLiveData<Boolean>() }

    var savingCount = 0


    fun loadNotes() {
        if (liveDataList.value == null)
            getAllNotes()
    }

    fun addOrUpdate(id: Int, title: String, text: String) {
        liveDataList.value?.forEach {
            if (it.id == id) {
                if (title == "" || text == "")
                    deleteById(id)
                else if (it.title != title || it.text != text)
                    delayFor({ update(NoteModel(title, text, Calendar.getInstance().timeInMillis, id)) })
                return
            }
        }
        if (title != "" && text != "")
            delayFor({ putNote(NoteModel(title, text, Calendar.getInstance().timeInMillis)) })
    }

    private fun getAllNotes() {
        dataRep.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setLiveData)
    }

    private fun startLoading() = liveDataLoading.postValue(true)

    private fun stopLoading() = liveDataLoading.postValue(false)

    private fun showError() = liveDataError.postValue(true)

    private fun setLiveData(data: List<NoteModel>) =
            liveDataList.postValue(data)


    private fun delayFor(func: () -> Unit, time: Long = 2000) {
        async(UI) {
            startLoading()
            delay(time)
            if (++savingCount == 3) {
                showError()
                savingCount = 0
            } else
                func()
            stopLoading()
        }
    }

    fun getNotesLiveData(): LiveData<List<NoteModel>> =
            liveDataList

    fun getLoadingLiveData(): LiveData<Boolean> =
            liveDataLoading

    fun getErrorLiveData(): LiveData<Boolean> =
            liveDataError

    fun delete(model: NoteModel) =
            bg { dataRep.delete(model) }

    private fun putNote(noteModel: NoteModel) =
            bg { dataRep.putNote(noteModel) }

    private fun update(noteModel: NoteModel) =
            bg { dataRep.updateNote(noteModel) }

    private fun deleteById(id: Int) =
            bg { dataRep.deleteById(id) }

    fun putDummy() {
        bg {
            dataRep.putNote(NoteModel("Title", "Text", Calendar.getInstance().timeInMillis))
        }
    }


}
