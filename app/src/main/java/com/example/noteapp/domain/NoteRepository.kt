package com.example.noteapp.domain

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes() : Flow<List<Note>>

    fun getNoteHighToLow(): Flow<List<Note>>

    fun getNoteLowToHigh(): Flow<List<Note>>

    suspend fun addNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun deleteAllNotes()

    suspend fun updateNote(note: Note)

    fun searchNoteWithTitle(query : String) : Flow<List<Note>>

}