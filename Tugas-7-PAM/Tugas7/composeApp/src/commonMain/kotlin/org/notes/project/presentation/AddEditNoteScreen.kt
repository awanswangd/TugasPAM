package org.notes.project.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    viewModel: NotesViewModel,
    onBackClick: () -> Unit
) {
    val title by viewModel.noteTitle.collectAsState()
    val content by viewModel.noteContent.collectAsState()
    val isEditing = viewModel.isEditing

    var titleError by remember { mutableStateOf<String?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Note") },
            text = { Text("Are you sure you want to delete this note?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteCurrentNote()
                        showDeleteDialog = false
                        onBackClick()
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (!isEditing) "Add Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (isEditing) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    viewModel.onTitleChange(it)
                    if (it.isNotBlank()) titleError = null
                },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                isError = titleError != null,
                supportingText = {
                    titleError?.let { Text(it) }
                }
            )

            OutlinedTextField(
                value = content,
                onValueChange = viewModel::onContentChange,
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                singleLine = false
            )

            Button(
                onClick = {
                    if (title.isBlank()) {
                        titleError = "Title cannot be empty"
                    } else {
                        viewModel.saveNote()
                        onBackClick()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Note")
            }
        }
    }
}
