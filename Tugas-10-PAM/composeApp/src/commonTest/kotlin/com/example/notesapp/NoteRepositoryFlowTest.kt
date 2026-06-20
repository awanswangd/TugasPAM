package com.example.notesapp.data.repository

import app.cash.turbine.test
import com.example.notesapp.domain.model.Note
import kotlinx.coroutines.test.runTest
import kotlin.test.*

/**
 * Flow testing dengan Turbine untuk NoteRepository
 */
class NoteRepositoryFlowTest {

    private lateinit var repository: NoteRepositoryImpl

    @BeforeTest
    fun setup() {
        repository = NoteRepositoryImpl()
    }

    @Test
    fun `getAllNotes emits empty list initially then updates on insert`() = runTest {
        repository.getAllNotes().test {
            // Emission pertama: list kosong
            val empty = awaitItem()
            assertTrue(empty.isEmpty())

            // Insert note
            repository.insertNote(Note(title = "Note A", content = "Content A"))

            // Emission kedua: list dengan 1 note
            val withOne = awaitItem()
            assertEquals(1, withOne.size)
            assertEquals("Note A", withOne[0].title)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAllNotes emits updated list after delete`() = runTest {
        val id = repository.insertNote(Note(title = "To Delete", content = ""))

        repository.getAllNotes().test {
            // Emission dengan note yang ada
            val withNote = awaitItem()
            assertEquals(1, withNote.size)

            // Hapus note
            repository.deleteNote(id)

            // Emission setelah delete: list kosong
            val afterDelete = awaitItem()
            assertTrue(afterDelete.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAllNotes emits multiple times on multiple inserts`() = runTest {
        repository.getAllNotes().test {
            awaitItem() // empty

            repository.insertNote(Note(title = "First", content = ""))
            val first = awaitItem()
            assertEquals(1, first.size)

            repository.insertNote(Note(title = "Second", content = ""))
            val second = awaitItem()
            assertEquals(2, second.size)

            repository.insertNote(Note(title = "Third", content = ""))
            val third = awaitItem()
            assertEquals(3, third.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAllNotes emits empty after deleteAll`() = runTest {
        repository.insertNote(Note(title = "A", content = ""))
        repository.insertNote(Note(title = "B", content = ""))

        repository.getAllNotes().test {
            val before = awaitItem()
            assertEquals(2, before.size)

            repository.deleteAllNotes()

            val after = awaitItem()
            assertTrue(after.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
