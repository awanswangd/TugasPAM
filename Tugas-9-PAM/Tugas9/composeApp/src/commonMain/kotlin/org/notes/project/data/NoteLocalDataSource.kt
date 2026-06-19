package org.notes.project.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import org.notes.project.db.AppDatabase
import org.notes.project.db.NoteEntity

class NoteLocalDataSource(
    database: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val queries = database.noteQueries

    fun getAllNotes(): Flow<List<NoteEntity>> {
        return queries.getAllNotes()
            .asFlow()
            .mapToList(dispatcher)
    }

    fun getNoteById(id: Long): Flow<NoteEntity?> {
        return queries.getNoteById(id)
            .asFlow()
            .mapToOneOrNull(dispatcher)
    }

    suspend fun insertNote(id: Long?, title: String, content: String, createdAt: Long) {
        queries.insertNote(id, title, content, createdAt)
    }

    suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id)
    }

    fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return queries.searchNotes(query)
            .asFlow()
            .mapToList(dispatcher)
    }
}
