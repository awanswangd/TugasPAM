package org.notes.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.notes.project.di.initKoin

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Tugas8 - Notes App",
        ) {
            App()
        }
    }
}
