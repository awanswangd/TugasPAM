package com.example.notesapp.domain.usecase

import com.example.notesapp.domain.model.Note
import kotlin.test.*

class NoteValidatorTest {

    private lateinit var validator: NoteValidator

    @BeforeTest
    fun setup() {
        validator = NoteValidator()
    }

    @Test
    fun `valid note with title and content returns true`() {
        // Arrange
        val note = Note(title = "Shopping List", content = "Buy milk and eggs")

        // Act
        val result = validator.isValid(note)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `empty title returns false`() {
        val note = Note(title = "", content = "Some content")
        assertFalse(validator.isValid(note))
    }

    @Test
    fun `blank title returns false`() {
        val note = Note(title = "   ", content = "Some content")
        assertFalse(validator.isValid(note))
    }

    @Test
    fun `title exactly at limit is valid`() {
        val note = Note(title = "a".repeat(200), content = "Content")
        assertTrue(validator.isValid(note))
    }

    @Test
    fun `title exceeding limit returns false`() {
        val note = Note(title = "a".repeat(201), content = "Content")
        assertFalse(validator.isValid(note))
    }

    @Test
    fun `valid note with empty content is valid`() {
        val note = Note(title = "Title Only", content = "")
        assertTrue(validator.isValid(note))
    }

    @Test
    fun `validate throws exception for empty title`() {
        val note = Note(title = "", content = "Content")
        assertFailsWith<ValidationException> {
            validator.validate(note)
        }
    }

    @Test
    fun `validate throws exception for title too long`() {
        val note = Note(title = "a".repeat(300), content = "Content")
        assertFailsWith<ValidationException> {
            validator.validate(note)
        }
    }

    @Test
    fun `validate does not throw for valid note`() {
        val note = Note(title = "Valid Title", content = "Valid Content")
        validator.validate(note) // Should not throw
    }
}
