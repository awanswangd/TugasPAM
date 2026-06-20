package com.example.notesapp.data.repository

import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NoteRepositoryImpl : NoteRepository {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    private var nextId = 1L

    override fun getAllNotes(): Flow<List<Note>> = _notes.asStateFlow()

    override suspend fun getNoteById(id: Long): Note? {
        return _notes.value.find { it.id == id }
    }

    override suspend fun insertNote(note: Note): Long {
        val newNote = note.copy(id = nextId++)
        _notes.update { current -> current + newNote }
        return newNote.id
    }

    override suspend fun updateNote(note: Note) {
        _notes.update { current ->
            current.map { if (it.id == note.id) note else it }
        }
    }

    override suspend fun deleteNote(id: Long) {
        _notes.update { current -> current.filter { it.id != id } }
    }

    override suspend fun deleteAllNotes() {
        _notes.update { emptyList() }
    }
}
