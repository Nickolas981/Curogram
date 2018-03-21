package com.example.ngumeniuk.curogram.noteList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.ngumeniuk.curogram.data.dataRepositories.NotesRepository
import com.example.ngumeniuk.curogram.data.models.NoteModel
import com.example.ngumeniuk.curogram.utils.BaseViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.coroutines.experimental.bg
import java.util.*
import java.util.concurrent.TimeUnit


class NotesViewModule : BaseViewModel() {

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
        addDisposable(dataRep.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setLiveData))
    }

    private fun startLoading() = liveDataLoading.postValue(true)

    private fun stopLoading() = liveDataLoading.postValue(false)

    private fun showError() = liveDataError.postValue(true)

    private fun setLiveData(data: List<NoteModel>) =
            liveDataList.postValue(data)


    private fun delayFor(func: () -> Unit) {
        addDisposable(Completable.complete()
                .delay(2, TimeUnit.SECONDS)
                .doOnSubscribe { startLoading() }
                .doOnComplete {
                    if (++savingCount == 3) {
                        savingCount = 0
                        showError()
                    } else
                        func()
                    stopLoading()
                    println(Thread.currentThread().name)
                }
                .subscribe())
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
            dataRep.putNote(noteModel)

    private fun update(noteModel: NoteModel) =
            dataRep.updateNote(noteModel)

    private fun deleteById(id: Int) =
            bg { dataRep.deleteById(id) }

    fun hideError() {
        liveDataError.postValue(false)
    }

    fun putDummy() {
        bg {
            dataRep.putNote(NoteModel("Title", "Text", Calendar.getInstance().timeInMillis))
        }
    }


}
