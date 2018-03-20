package com.example.ngumeniuk.curogram.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.ngumeniuk.curogram.data.dao.NotesDao
import com.example.ngumeniuk.curogram.data.models.NoteModel


@Database(entities = arrayOf(NoteModel::class), version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}