package com.example.notesapp.ui.viewmodel

import com.example.notesapp.domain.model.Note

sealed class NotesUiState {
    data object Loading : NotesUiState()
    data class Success(val notes: List<Note>) : NotesUiState()
    data class Error(val message: String) : NotesUiState()
}
