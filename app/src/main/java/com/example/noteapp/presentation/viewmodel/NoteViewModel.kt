package com.example.noteapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.model.Note
import com.example.noteapp.domain.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private var noteRepository: NoteRepository) : ViewModel() {

    val allNotes = noteRepository.getAllNotes().asLiveData()

    private var _isCreatedNote =  MutableSharedFlow<Boolean>()
    val isCreatedNote : SharedFlow<Boolean> = _isCreatedNote

    suspend fun addNote(note: Note) {
        if(note.title.isEmpty()){
            _isCreatedNote.emit(false)
            return

        }
        viewModelScope.launch {
            noteRepository.addNote(note)
            _isCreatedNote.emit(true)

        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            noteRepository.deleteAllNotes()
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }

    fun searchNoteWithTitle(query: String): LiveData<List<Note>> {
        return noteRepository.searchNoteWithTitle(query).asLiveData()
    }


}