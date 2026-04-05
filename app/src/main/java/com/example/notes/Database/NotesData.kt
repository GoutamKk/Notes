package com.example.notes.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class NotesData(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String? ="",
    var text: String? ="",
    var time: String
)
