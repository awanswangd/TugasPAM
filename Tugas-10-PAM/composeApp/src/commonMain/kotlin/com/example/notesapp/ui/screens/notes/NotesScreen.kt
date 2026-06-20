package com.example.notesapp.ui.screens.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.notesapp.domain.model.Note
import com.example.notesapp.ui.viewmodel.NotesUiState
import com.example.notesapp.ui.viewmodel.NotesViewModel
import com.example.notesapp.util.TestTags
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    NotesScreenContent(
        uiState = uiState,
        onAddNote = { title, content -> viewModel.addNote(title, content) },
        onDeleteNote = { id -> viewModel.deleteNote(id) }
    )
}

@Composable
fun NotesScreenContent(
    uiState: NotesUiState,
    onAddNote: (String, String) -> Unit,
    onDeleteNote: (Long) -> Unit
) {
    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Notes") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Input section
            OutlinedTextField(
                value = titleText,
                onValueChange = { titleText = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.TITLE_INPUT)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = contentText,
                onValueChange = { contentText = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.CONTENT_INPUT)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (titleText.isNotBlank()) {
                        onAddNote(titleText, contentText)
                        titleText = ""
                        contentText = ""
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .testTag(TestTags.ADD_BUTTON)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add")
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            // Content section based on state
            when (uiState) {
                is NotesUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.testTag(TestTags.LOADING_INDICATOR)
                        )
                    }
                }

                is NotesUiState.Error -> {
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.testTag(TestTags.ERROR_MESSAGE)
                    )
                }

                is NotesUiState.Success -> {
                    if (uiState.notes.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .testTag(TestTags.EMPTY_STATE),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No notes yet. Add your first note!",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .testTag(TestTags.NOTES_LIST),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.notes, key = { it.id }) { note ->
                                NoteItem(
                                    note = note,
                                    onDelete = { onDeleteNote(note.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TestTags.NOTE_ITEM),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium
                )
                if (note.content.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = note.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag(TestTags.DELETE_BUTTON)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete note",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
