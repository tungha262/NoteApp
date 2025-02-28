package com.example.noteapp.presentation.viewmodel

import android.text.TextUtils
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

    private var _isCreatedNote = MutableSharedFlow<Boolean>()
    val isCreatedNote: SharedFlow<Boolean> = _isCreatedNote

    private var _isUpdatedNote = MutableSharedFlow<Boolean>()
    val isUpdatedNote: SharedFlow<Boolean> = _isUpdatedNote


    suspend fun addNote(note: Note) {
        if (!checkValid(note)) {
            _isCreatedNote.emit(false)
            return
        }
        viewModelScope.launch {
            noteRepository.addNote(note)
            _isCreatedNote.emit(true)

        }
    }

    suspend fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    suspend fun deleteAllNotes() {
        viewModelScope.launch {
            noteRepository.deleteAllNotes()
        }
    }

    suspend fun updateNote(note: Note) {
        if(!checkValid(note)){
            _isUpdatedNote.emit(false)
            return
        }
        viewModelScope.launch {
            noteRepository.updateNote(note)
            _isUpdatedNote.emit(true)
        }
    }

    fun searchNoteWithTitle(query: String): LiveData<List<Note>> {
        return noteRepository.searchNoteWithTitle(query).asLiveData()
    }

    private fun checkValid(note: Note): Boolean {
        return !TextUtils.isEmpty(note.title)
    }

}