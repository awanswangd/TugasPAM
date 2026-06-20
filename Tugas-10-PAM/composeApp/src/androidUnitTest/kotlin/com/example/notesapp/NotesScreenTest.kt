package com.example.notesapp.ui.screens.notes

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.notesapp.domain.model.Note
import com.example.notesapp.ui.viewmodel.NotesUiState
import com.example.notesapp.util.TestTags
import org.junit.Rule
import org.junit.Test

/**
 * UI Tests untuk NotesScreen menggunakan Compose Test + Test Tags
 *
 * Jalankan dengan: ./gradlew connectedAndroidTest
 */
class NotesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `empty state shows empty message when no notes`() {
        composeTestRule.setContent {
            NotesScreenContent(
                uiState = NotesUiState.Success(emptyList()),
                onAddNote = { _, _ -> },
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.EMPTY_STATE)
            .assertIsDisplayed()
    }

    @Test
    fun `notes list is displayed when notes exist`() {
        val notes = listOf(
            Note(id = 1L, title = "Shopping", content = "Buy milk"),
            Note(id = 2L, title = "Work", content = "Finish report")
        )

        composeTestRule.setContent {
            NotesScreenContent(
                uiState = NotesUiState.Success(notes),
                onAddNote = { _, _ -> },
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.NOTES_LIST)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Shopping")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Work")
            .assertIsDisplayed()
    }

    @Test
    fun `add button is displayed and clickable`() {
        composeTestRule.setContent {
            NotesScreenContent(
                uiState = NotesUiState.Success(emptyList()),
                onAddNote = { _, _ -> },
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.ADD_BUTTON)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun `typing in title input updates text field`() {
        composeTestRule.setContent {
            NotesScreenContent(
                uiState = NotesUiState.Success(emptyList()),
                onAddNote = { _, _ -> },
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.TITLE_INPUT)
            .performTextInput("My New Note")

        composeTestRule
            .onNodeWithTag(TestTags.TITLE_INPUT)
            .assertTextContains("My New Note")
    }

    @Test
    fun `clicking add button calls onAddNote with input text`() {
        var capturedTitle = ""
        var capturedContent = ""

        composeTestRule.setContent {
            NotesScreenContent(
                uiState = NotesUiState.Success(emptyList()),
                onAddNote = { title, content ->
                    capturedTitle = title
                    capturedContent = content
                },
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.TITLE_INPUT)
            .performTextInput("Shopping List")

        composeTestRule
            .onNodeWithTag(TestTags.CONTENT_INPUT)
            .performTextInput("Milk, Eggs, Bread")

        composeTestRule
            .onNodeWithTag(TestTags.ADD_BUTTON)
            .performClick()

        assert(capturedTitle == "Shopping List")
        assert(capturedContent == "Milk, Eggs, Bread")
    }

    @Test
    fun `loading indicator shown when state is loading`() {
        composeTestRule.setContent {
            NotesScreenContent(
                uiState = NotesUiState.Loading,
                onAddNote = { _, _ -> },
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LOADING_INDICATOR)
            .assertIsDisplayed()
    }

    @Test
    fun `error message shown when state is error`() {
        composeTestRule.setContent {
            NotesScreenContent(
                uiState = NotesUiState.Error("Something went wrong"),
                onAddNote = { _, _ -> },
                onDeleteNote = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.ERROR_MESSAGE)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Something went wrong")
            .assertIsDisplayed()
    }
}
