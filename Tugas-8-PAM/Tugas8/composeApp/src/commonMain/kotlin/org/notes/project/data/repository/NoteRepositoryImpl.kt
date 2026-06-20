package org.notes.project.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.notes.project.data.NoteLocalDataSource
import org.notes.project.data.mapper.toNote
import org.notes.project.domain.model.Note
import org.notes.project.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val localDataSource: NoteLocalDataSource
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return localDataSource.getAllNotes().map { entities ->
            entities.map { it.toNote() }
        }
    }

    override fun getNoteById(id: Long): Flow<Note?> {
        return localDataSource.getNoteById(id).map { it?.toNote() }
    }

    override suspend fun insertNote(note: Note) {
        localDataSource.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            createdAt = note.createdAt
        )
    }

    override suspend fun deleteNoteById(id: Long) {
        localDataSource.deleteNoteById(id)
    }

    override fun searchNotes(query: String): Flow<List<Note>> {
        return localDataSource.searchNotes(query).map { entities ->
            entities.map { it.toNote() }
        }
    }
}
