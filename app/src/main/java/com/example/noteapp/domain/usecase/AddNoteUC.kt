package com.example.noteapp.domain.usecase


import android.text.TextUtils
import com.example.noteapp.data.model.Note
import com.example.noteapp.domain.repo.NoteRepository
import com.example.noteapp.domain.state.EditNoteState
import javax.inject.Inject

class AddNoteUC @Inject constructor(private val noteRepository: NoteRepository)  {
    suspend operator fun invoke(note: Note) : EditNoteState{
        if(TextUtils.isEmpty(note.title)){
            return EditNoteState.Error("Title can't be empty")
        }
        else{
            noteRepository.addNote(note)
            return EditNoteState.Success("Note created successfully")
        }
    }
}