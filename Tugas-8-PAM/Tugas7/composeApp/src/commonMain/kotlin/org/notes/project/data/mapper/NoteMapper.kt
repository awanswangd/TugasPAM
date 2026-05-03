package org.notes.project.data.mapper

import org.notes.project.db.NoteEntity
import org.notes.project.domain.model.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id ?: 0L, // SQLDelight id is usually non-null for the entity class but can be null for insertion
        title = title,
        content = content,
        createdAt = createdAt
    )
}
