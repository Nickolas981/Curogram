package com.example.ngumeniuk.curogram.data.dataRepositories

import com.example.ngumeniuk.curogram.App
import com.example.ngumeniuk.curogram.data.dataSource.NotesDataSource
import com.example.ngumeniuk.curogram.data.models.NoteModel


class NotesRepository : NotesDataSource {
    private val notesDao by lazy { App.instance.getDatabase()!!.notesDao() }

    override fun getAll() = notesDao.getAll()
    override fun getById(noteId: Int) = notesDao.selectById(noteId)
    override fun delete(note: NoteModel) = notesDao.delete(note)
    override fun putNote(note: NoteModel) = notesDao.putNote(note)
    override fun updateNote(note: NoteModel) = notesDao.updateNote(note)
    override fun dropTable() = notesDao.dropTable()
}