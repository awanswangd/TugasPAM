package org.notes.project.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.notes.project.model.sampleNotes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: Int,           // Diterima dari navigation argument
    onBack: () -> Unit,    // popBackStack()
    onEdit: (Int) -> Unit  // Navigate ke EditNote dengan noteId
) {
    val note = sampleNotes.find { it.id == noteId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(note?.title ?: "Detail", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { onEdit(noteId) }) {
                        Icon(Icons.Default.Edit, "Edit")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        note?.let {
            Column(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
            ) {
                // Menampilkan noteId (bukti argument berhasil dikirim)
                Surface(color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.small) {
                    Text("Note ID: $noteId",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(8.dp, 4.dp))
                }
                Spacer(Modifier.height(16.dp))
                Text(it.title, style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(it.createdAt, style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline)
                Spacer(Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(Modifier.height(16.dp))
                Text(it.content, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}