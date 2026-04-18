package org.notes.project.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.notes.project.components.NoteCard
import org.notes.project.model.sampleNotes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(onNoteClick: (Int) -> Unit) {
    val favorites = sampleNotes.filter { it.isFavorite }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("❤️ Favorit", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer)
            )
        }
    ) { padding ->
        if (favorites.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                Text("Belum ada catatan favorit")
            }
        } else {
            LazyColumn(
                Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favorites) { note ->
                    NoteCard(note = note, onClick = { onNoteClick(note.id) })
                }
            }
        }
    }
}