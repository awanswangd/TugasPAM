package org.notes.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * App: Entry point composable utama.
 * Dipanggil dari MainActivity di Android.
 */
@Composable
fun App() {
    MaterialTheme {
        MainScreen()
    }
}