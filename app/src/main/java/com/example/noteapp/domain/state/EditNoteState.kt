package com.example.noteapp.domain.state

sealed class EditNoteState {
    data class Success(val message:String) : EditNoteState()
    data class Error(val message:String) : EditNoteState()
}