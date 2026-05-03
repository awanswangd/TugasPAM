package org.notes.project.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.notes.project.domain.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(
    viewModel: NotesViewModel,
    onNoteClick: (Note) -> Unit,
    onAddNoteClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val notes by viewModel.notes.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Notes") },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNoteClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search notes...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (isSearching) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(
                    items = notes,
                    key = { it.id ?: 0L }
                ) { note ->
                    NoteItem(
                        note = note,
                        onNoteClick = { onNoteClick(note) },
                        onDeleteClick = { note.id?.let { viewModel.deleteNote(it) } }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    note: Note,
    onNoteClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val swipeToDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDeleteClick()
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = swipeToDismissState,
        backgroundContent = {
            val color = if (swipeToDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                Color.Red
            } else Color.Transparent

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(color, MaterialTheme.shapes.medium),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        },
        enableDismissFromStartToEnd = false
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onNoteClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = note.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = note.content,
                    maxLines = 2,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
