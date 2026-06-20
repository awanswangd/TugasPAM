package org.notes.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.notes.project.domain.model.Note
import org.notes.project.domain.repository.NoteRepository

class NotesViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    val notes: StateFlow<List<Note>> = _searchText
        .debounce(300L)
        .onEach { _isSearching.update { true } }
        .flatMapLatest { text ->
            if (text.isBlank()) {
                repository.getAllNotes()
            } else {
                repository.searchNotes(text)
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // State for Add/Edit Screen
    private val _noteTitle = MutableStateFlow("")
    val noteTitle = _noteTitle.asStateFlow()

    private val _noteContent = MutableStateFlow("")
    val noteContent = _noteContent.asStateFlow()

    private var currentNoteId: Long? = null
    
    val isEditing: Boolean get() = currentNoteId != null

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onTitleChange(title: String) {
        _noteTitle.value = title
    }

    fun onContentChange(content: String) {
        _noteContent.value = content
    }

    fun setNoteToEdit(note: Note?) {
        currentNoteId = note?.id
        _noteTitle.value = note?.title ?: ""
        _noteContent.value = note?.content ?: ""
    }

    fun saveNote() {
        val title = _noteTitle.value
        val content = _noteContent.value
        if (title.isBlank() && content.isBlank()) return

        viewModelScope.launch {
            repository.insertNote(
                Note(
                    id = currentNoteId,
                    title = title,
                    content = content,
                    createdAt = Clock.System.now().toEpochMilliseconds()
                )
            )
            // Reset state after saving
            setNoteToEdit(null)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNoteById(id)
        }
    }

    fun deleteCurrentNote() {
        currentNoteId?.let { id ->
            deleteNote(id)
        }
    }
}
