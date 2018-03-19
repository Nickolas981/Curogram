package com.example.ngumeniuk.curogram.data.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteModel(
        @PrimaryKey val id : Int,
        val title: String,
        val text: String,
        val date: String
)