package com.example.ngumeniuk.curogram.data.dao

import android.arch.persistence.room.*
import com.example.ngumeniuk.curogram.data.models.NoteModel
import io.reactivex.Single


@Dao
interface NotesDao {
    @Insert
    fun putNote(note: NoteModel)

    @Delete
    fun delete(note: NoteModel)

    @Query("SELECT * FROM notes")
    fun getAll(): Single<NoteModel>

    @Query("SELECT * FROM notes WHERE id LIKE :id")
    fun selectById(id: Int)

    @Update
    fun updateNote(note: NoteModel)
}