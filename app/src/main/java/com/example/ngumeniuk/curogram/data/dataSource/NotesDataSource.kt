package com.example.ngumeniuk.curogram.data.dataSource

import com.example.ngumeniuk.curogram.data.models.NoteModel
import io.reactivex.Flowable
import io.reactivex.Single


interface NotesDataSource {

    fun getAll(): Flowable<List<NoteModel>>

    fun getById(noteId: Int): Single<NoteModel>

    fun delete(note: NoteModel)

    fun updateNote(note: NoteModel)

    fun dropTable()

    fun putNote(note: NoteModel)
}