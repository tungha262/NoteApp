package com.example.noteapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp.data.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Query("SELECT * FROM note_data")
    fun getAllNotes() : Flow<List<Note>>

    @Insert
    suspend fun addNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note_data")
    suspend fun deleteAllNotes()

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM note_data WHERE title LIKE :query")
    fun searchNoteWithTitle(query : String) : Flow<List<Note>>

    @Query("SELECT * FROM note_data ORDER BY priority ASC ")
    fun getNoteHighToLow() : Flow<List<Note>>

    @Query("SELECT * FROM note_data ORDER BY priority DESC ")
    fun getNoteLowToHigh() : Flow<List<Note>>

}