package org.newsfeed.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Newsfeedtugas2",
    ) {
        App()
    }
}