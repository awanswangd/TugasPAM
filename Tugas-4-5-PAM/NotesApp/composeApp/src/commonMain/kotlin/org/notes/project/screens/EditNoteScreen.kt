package org.notes.project.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.notes.project.model.sampleNotes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    noteId: Int,         // Argument dari navigation
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val original = sampleNotes.find { it.id == noteId }
    // Pre-fill form dengan data yang sudah ada
    var title   by remember { mutableStateOf(original?.title ?: "") }
    var content by remember { mutableStateOf(original?.content ?: "") }
    var showErr by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Catatan", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Batal")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (title.isBlank()) showErr = true else onSave()
                    }) {
                        Icon(Icons.Default.Check, "Simpan")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Menampilkan noteId (bukti argument diterima)
            Surface(color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = MaterialTheme.shapes.small) {
                Text("Mengedit Note ID: $noteId",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(8.dp, 4.dp))
            }
            OutlinedTextField(
                value = title,
                onValueChange = { title = it; if (it.isNotBlank()) showErr = false },
                label = { Text("Judul") },
                modifier = Modifier.fillMaxWidth(),
                isError = showErr,
                supportingText = { if (showErr) Text("Judul wajib diisi",
                    color = MaterialTheme.colorScheme.error) },
                singleLine = true
            )
            OutlinedTextField(
                value = content, onValueChange = { content = it },
                label = { Text("Isi Catatan") },
                modifier = Modifier.fillMaxWidth().weight(1f), maxLines = 20
            )
            Button(onClick = { if (title.isBlank()) showErr = true else onSave() },
                modifier = Modifier.fillMaxWidth()) {
                Text("Simpan Perubahan")
            }
        }
    }
}