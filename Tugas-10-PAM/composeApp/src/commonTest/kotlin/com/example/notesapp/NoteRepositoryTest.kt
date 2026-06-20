package com.example.notesapp.data.repository

import app.cash.turbine.test
import com.example.notesapp.domain.model.Note
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class NoteRepositoryTest {

    private lateinit var repository: NoteRepositoryImpl

    private val testNote1 = Note(title = "Note 1", content = "Content 1")
    private val testNote2 = Note(title = "Note 2", content = "Content 2")

    @BeforeTest
    fun setup() {
        repository = NoteRepositoryImpl()
    }

    @Test
    fun `getAllNotes initially emits empty list`() = runTest {
        repository.getAllNotes().test {
            val items = awaitItem()
            assertTrue(items.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insertNote adds note to repository`() = runTest {
        repository.insertNote(testNote1)

        repository.getAllNotes().test {
            val items = awaitItem()
            assertEquals(1, items.size)
            assertEquals("Note 1", items[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insertNote returns assigned id`() = runTest {
        val id = repository.insertNote(testNote1)
        assertTrue(id > 0)
    }

    @Test
    fun `insertNote multiple notes assigns unique ids`() = runTest {
        val id1 = repository.insertNote(testNote1)
        val id2 = repository.insertNote(testNote2)
        assertNotEquals(id1, id2)
    }

    @Test
    fun `getNoteById returns correct note`() = runTest {
        val id = repository.insertNote(testNote1)
        val found = repository.getNoteById(id)
        assertNotNull(found)
        assertEquals("Note 1", found.title)
    }

    @Test
    fun `getNoteById returns null for non-existent id`() = runTest {
        val found = repository.getNoteById(999L)
        assertNull(found)
    }

    @Test
    fun `updateNote modifies existing note`() = runTest {
        val id = repository.insertNote(testNote1)
        val updatedNote = testNote1.copy(id = id, title = "Updated Title")
        repository.updateNote(updatedNote)

        val found = repository.getNoteById(id)
        assertEquals("Updated Title", found?.title)
    }

    @Test
    fun `deleteNote removes note from list`() = runTest {
        val id = repository.insertNote(testNote1)
        repository.deleteNote(id)

        repository.getAllNotes().test {
            val items = awaitItem()
            assertTrue(items.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `deleteAllNotes clears all notes`() = runTest {
        repository.insertNote(testNote1)
        repository.insertNote(testNote2)
        repository.deleteAllNotes()

        repository.getAllNotes().test {
            val items = awaitItem()
            assertTrue(items.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAllNotes emits updated list after insert`() = runTest {
        repository.getAllNotes().test {
            // Initial empty state
            val initial = awaitItem()
            assertTrue(initial.isEmpty())

            // Insert note
            repository.insertNote(testNote1)

            // Updated list with 1 note
            val updated = awaitItem()
            assertEquals(1, updated.size)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
