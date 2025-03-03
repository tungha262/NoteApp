package com.example.noteapp.presentation.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.model.Note
import com.example.noteapp.domain.repo.NoteRepository
import com.example.noteapp.domain.state.EditNoteState
import com.example.noteapp.domain.usecase.AddNoteUC
import com.example.noteapp.domain.usecase.DeleteAllNotesUC
import com.example.noteapp.domain.usecase.DeleteNoteUC
import com.example.noteapp.domain.usecase.GetAllNotesUC
import com.example.noteapp.domain.usecase.GetNoteHighToLowUC
import com.example.noteapp.domain.usecase.GetNoteLowToHighUC
import com.example.noteapp.domain.usecase.SearchNoteWithTitleUC
import com.example.noteapp.domain.usecase.UpdateNoteUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private var addNoteUC: AddNoteUC,
    private var deleteNoteUC: DeleteNoteUC,
    private var deleteAllNotesUC: DeleteAllNotesUC,
    private var getAllNotesUC: GetAllNotesUC,
    private var getNoteHighToLowUC: GetNoteHighToLowUC,
    private var getNoteLowToHighUC: GetNoteLowToHighUC,
    private var searchNoteWithTitleUC: SearchNoteWithTitleUC,
    private var updateNoteUC: UpdateNoteUC,
) : ViewModel() {

    val allNotes = getAllNotesUC().asLiveData()
    val allNotesHighToLow = getNoteHighToLowUC().asLiveData()
    val allNotesLowToHigh = getNoteLowToHighUC().asLiveData()

    private var _isCreatedNote = MutableSharedFlow<EditNoteState>()
    val isCreatedNote: SharedFlow<EditNoteState> = _isCreatedNote

    private var _isUpdatedNote = MutableSharedFlow<EditNoteState>()
    val isUpdatedNote: SharedFlow<EditNoteState> = _isUpdatedNote


    private var _filterState = MutableLiveData(1)
    val filterState get() = _filterState


    fun addNote(note: Note) {
        viewModelScope.launch {
            val result = addNoteUC(note)
            _isCreatedNote.emit(result)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUC(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            deleteAllNotesUC()
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            val result = updateNoteUC(note)
            _isUpdatedNote.emit(result)
        }
    }

    fun searchNoteWithTitle(query: String): LiveData<List<Note>> {
        return searchNoteWithTitleUC(query).asLiveData()
    }

    fun setStateFilter(state: Int) {
        _filterState.value = state
    }

}