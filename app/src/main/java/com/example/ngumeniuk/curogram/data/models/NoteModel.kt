package com.example.ngumeniuk.curogram.data.models

import android.arch.persistence.room.Entity

@Entity(tableName = "notes")
data class NoteModel(
        val title: String,
        val text: String,
        val date: Long
)