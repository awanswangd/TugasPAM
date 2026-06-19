package org.notes.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.notes.project.domain.model.Note

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    fun getNoteById(id: Long): Flow<Note?>
    suspend fun insertNote(note: Note)
    suspend fun deleteNoteById(id: Long)
    fun searchNotes(query: String): Flow<List<Note>>
}
