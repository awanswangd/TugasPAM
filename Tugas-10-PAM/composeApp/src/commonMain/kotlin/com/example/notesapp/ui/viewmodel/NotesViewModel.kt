package com.example.notesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.repository.NoteRepository
import com.example.notesapp.domain.usecase.NoteValidator
import com.example.notesapp.domain.usecase.ValidationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: NoteRepository,
    private val validator: NoteValidator = NoteValidator()
) : ViewModel() {

    private val _uiState = MutableStateFlow<NotesUiState>(NotesUiState.Loading)
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _uiState.value = NotesUiState.Loading
            repository.getAllNotes()
                .catch { e ->
                    _uiState.value = NotesUiState.Error(e.message ?: "Unknown error")
                }
                .collect { notes ->
                    _uiState.value = NotesUiState.Success(notes)
                }
        }
    }

    fun addNote(title: String, content: String) {
        val note = Note(title = title, content = content)
        try {
            validator.validate(note)
            viewModelScope.launch {
                repository.insertNote(note)
            }
        } catch (e: ValidationException) {
            _uiState.value = NotesUiState.Error(e.message ?: "Validation error")
        }
    }

    fun updateNote(note: Note) {
        try {
            validator.validate(note)
            viewModelScope.launch {
                repository.updateNote(note)
            }
        } catch (e: ValidationException) {
            _uiState.value = NotesUiState.Error(e.message ?: "Validation error")
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }
}
