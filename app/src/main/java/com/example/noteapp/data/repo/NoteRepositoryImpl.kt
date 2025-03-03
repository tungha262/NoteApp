package com.example.noteapp.data.repo

import com.example.noteapp.data.model.Note
import com.example.noteapp.data.room.NoteDao
import com.example.noteapp.domain.repo.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao)  : NoteRepository {
    override fun getNoteHighToLow(): Flow<List<Note>> {
        return noteDao.getNoteHighToLow()
    }

    override fun getNoteLowToHigh(): Flow<List<Note>> {
        return noteDao.getNoteLowToHigh()
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    override fun searchNoteWithTitle(query: String): Flow<List<Note>> {
        return noteDao.searchNoteWithTitle(query)
    }
}