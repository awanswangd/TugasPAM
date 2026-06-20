package org.notes.project

import androidx.compose.ui.window.ComposeUIViewController
import org.notes.project.di.initKoin

private val koinStarted = run {
    initKoin()
    true
}

fun MainViewController() = ComposeUIViewController {
    // Memastikan Koin sudah diinisialisasi sebelum App() dijalankan
    koinStarted
    App()
}
