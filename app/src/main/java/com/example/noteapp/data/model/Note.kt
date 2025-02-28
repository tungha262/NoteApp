package com.example.noteapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_data")
data class Note(
    @PrimaryKey(autoGenerate = true) val id :Int,
    val title : String,
    val content  :String,
    val date:String,
    val priority:String
)  :Serializable