package com.example.notesapp.ui.viewmodel

import app.cash.turbine.test
import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.repository.NoteRepository
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class NotesViewModelTest {

    // Buat mock NoteRepository
    private val mockRepository = mockk<NoteRepository>()
    private lateinit var viewModel: NotesViewModel

    private val testNote = Note(id = 1L, title = "Test Note", content = "Test Content")

    @BeforeTest
    fun setup() {
        // Stub getAllNotes to return flow dengan satu note
        coEvery { mockRepository.getAllNotes() } returns flowOf(listOf(testNote))
        // Buat ViewModel dengan mock dependency
        viewModel = NotesViewModel(mockRepository)
    }

    @AfterTest
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `initial state transitions from loading to success`() = runTest {
        // Setup ViewModel baru agar bisa observe dari awal
        coEvery { mockRepository.getAllNotes() } returns flowOf(listOf(testNote))
        val vm = NotesViewModel(mockRepository)

        vm.uiState.test {
            // Bisa langsung Success karena flow di-collect segera
            val state = awaitItem()
            // State bisa Loading atau langsung Success tergantung timing
            assertTrue(state is NotesUiState.Loading || state is NotesUiState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState emits success with notes from repository`() = runTest {
        viewModel.uiState.test {
            // Skip loading state jika ada
            val item = awaitItem()
            val successState = if (item is NotesUiState.Success) item else {
                awaitItem() as NotesUiState.Success
            }
            assertEquals(1, successState.notes.size)
            assertEquals("Test Note", successState.notes[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `addNote calls repository insertNote`() = runTest {
        coEvery { mockRepository.insertNote(any()) } returns 2L

        viewModel.addNote("New Note", "New Content")

        coVerify {
            mockRepository.insertNote(match {
                it.title == "New Note" && it.content == "New Content"
            })
        }
    }

    @Test
    fun `addNote with empty title does not call repository`() = runTest {
        viewModel.addNote("", "Some content")

        coVerify(exactly = 0) { mockRepository.insertNote(any()) }
    }

    @Test
    fun `deleteNote calls repository deleteNote with correct id`() = runTest {
        coEvery { mockRepository.deleteNote(any()) } just Runs

        viewModel.deleteNote(1L)

        coVerify { mockRepository.deleteNote(1L) }
    }

    @Test
    fun `updateNote calls repository updateNote`() = runTest {
        coEvery { mockRepository.updateNote(any()) } just Runs

        val updatedNote = testNote.copy(title = "Updated Title")
        viewModel.updateNote(updatedNote)

        coVerify { mockRepository.updateNote(match { it.title == "Updated Title" }) }
    }

    @Test
    fun `deleteAllNotes calls repository deleteAllNotes`() = runTest {
        coEvery { mockRepository.deleteAllNotes() } just Runs

        viewModel.deleteAllNotes()

        coVerify { mockRepository.deleteAllNotes() }
    }

    @Test
    fun `error from repository emits error state`() = runTest {
        coEvery { mockRepository.getAllNotes() } returns
            kotlinx.coroutines.flow.flow { throw Exception("DB error") }

        val vm = NotesViewModel(mockRepository)

        vm.uiState.test {
            skipItems(1) // Skip Loading
            val state = awaitItem()
            assertTrue(state is NotesUiState.Error)
            assertEquals("DB error", (state as NotesUiState.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
