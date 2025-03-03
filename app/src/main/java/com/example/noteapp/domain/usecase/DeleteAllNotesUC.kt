package com.example.noteapp.domain.usecase


import com.example.noteapp.data.model.Note
import com.example.noteapp.domain.repo.NoteRepository
import javax.inject.Inject

class DeleteAllNotesUC @Inject constructor(private val noteRepository: NoteRepository)  {
    suspend operator fun invoke(){
        noteRepository.deleteAllNotes()
    }
}