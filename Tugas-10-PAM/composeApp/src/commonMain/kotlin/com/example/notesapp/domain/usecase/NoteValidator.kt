package com.example.notesapp.domain.usecase

import com.example.notesapp.domain.model.Note

class NoteValidator {

    fun isValid(note: Note): Boolean {
        return note.title.isNotBlank() && note.title.length <= 200
    }

    fun validate(note: Note) {
        if (note.title.isBlank()) throw ValidationException("Title cannot be empty")
        if (note.title.length > 200) throw ValidationException("Title is too long (max 200 chars)")
    }
}

class ValidationException(message: String) : Exception(message)
