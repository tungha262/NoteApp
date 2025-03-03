package com.example.noteapp.domain.usecase

import com.example.noteapp.data.model.Note
import com.example.noteapp.domain.repo.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUC @Inject constructor(private val noteRepository: NoteRepository) {
    operator fun invoke() : Flow<List<Note>> {
        return noteRepository.getAllNotes()
    }
}