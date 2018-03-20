package com.example.ngumeniuk.curogram.data.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.ngumeniuk.curogram.utils.Differeble

@Entity(tableName = "notes")
data class NoteModel(
        var title: String,
        var text: String,
        var date: Long,
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
) : Differeble {
    override fun getIdToDiff(): Int = id
}