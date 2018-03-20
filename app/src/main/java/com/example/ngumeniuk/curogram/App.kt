package com.example.ngumeniuk.curogram

import android.app.Application
import android.arch.persistence.room.Room
import com.example.ngumeniuk.curogram.database.NotesDatabase

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    private var database: NotesDatabase? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, NotesDatabase::class.java, "notesDao")
                .build()
    }

    fun getDatabase(): NotesDatabase? {
        return database
    }

}