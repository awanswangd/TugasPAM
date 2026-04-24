package org.notes.project

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.russhwolf.settings.Settings
import org.notes.project.data.NoteLocalDataSource
import org.notes.project.data.repository.NoteRepositoryImpl
import org.notes.project.data.settings.SettingsManager
import org.notes.project.data.settings.ThemeConfig
import org.notes.project.db.AppDatabase
import org.notes.project.db.DatabaseDriverFactory
import org.notes.project.presentation.*
import androidx.activity.compose.BackHandler

@Composable
fun App(databaseDriverFactory: DatabaseDriverFactory) {
    val database = remember { AppDatabase(databaseDriverFactory.createDriver()) }
    val repository = remember { NoteRepositoryImpl(NoteLocalDataSource(database)) }
    val settingsManager = remember { SettingsManager(Settings()) }

    val theme by settingsManager.theme.collectAsState()
    val isDarkTheme = when (theme) {
        ThemeConfig.LIGHT -> false
        ThemeConfig.DARK -> true
        ThemeConfig.SYSTEM -> isSystemInDarkTheme()
    }

    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        val viewModel: NotesViewModel = viewModel { NotesViewModel(repository) }
        
        var currentScreen by remember { mutableStateOf<Screen>(Screen.NotesList) }

        // Handle system back button
        if (currentScreen != Screen.NotesList) {
            BackHandler {
                currentScreen = Screen.NotesList
            }
        }

        when (val screen = currentScreen) {
            is Screen.NotesList -> {
                NotesListScreen(
                    viewModel = viewModel,
                    onNoteClick = { note ->
                        viewModel.setNoteToEdit(note)
                        currentScreen = Screen.AddEditNote
                    },
                    onAddNoteClick = {
                        viewModel.setNoteToEdit(null)
                        currentScreen = Screen.AddEditNote
                    },
                    onSettingsClick = {
                        currentScreen = Screen.Settings
                    }
                )
            }
            is Screen.AddEditNote -> {
                AddEditNoteScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        currentScreen = Screen.NotesList
                    }
                )
            }
            is Screen.Settings -> {
                SettingsScreen(
                    settingsManager = settingsManager,
                    onBackClick = {
                        currentScreen = Screen.NotesList
                    }
                )
            }
        }
    }
}

sealed class Screen {
    data object NotesList : Screen()
    data object AddEditNote : Screen()
    data object Settings : Screen()
}
