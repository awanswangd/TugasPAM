package org.notes.project.model

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false,
    val createdAt: String = ""
)

val sampleNotes = listOf(
    Note(
        id = 1,
        title = "Belajar Kotlin",
        content = "Kotlin adalah bahasa pemrograman modern yang berjalan di JVM. " +
                "Kotlin memiliki sintaks yang lebih ringkas dibanding Java dan mendukung null safety.",
        isFavorite = true,
        createdAt = "2025-04-01"
    ),
    Note(
        id = 2,
        title = "Compose Multiplatform",
        content = "Compose Multiplatform memungkinkan kita membuat UI yang bisa berjalan " +
                "di Android, iOS, dan Desktop menggunakan satu codebase.",
        isFavorite = false,
        createdAt = "2025-04-02"
    ),
    Note(
        id = 3,
        title = "Navigasi Antar Layar",
        content = "Navigation Component di Compose menggunakan NavHost, NavController, " +
                "dan Routes untuk mengatur perpindahan antar screen.",
        isFavorite = true,
        createdAt = "2025-04-03"
    ),
    Note(
        id = 4,
        title = "State Management",
        content = "State di Compose dikelola menggunakan remember + mutableStateOf. " +
                "Untuk state lebih kompleks, gunakan ViewModel dengan StateFlow.",
        isFavorite = false,
        createdAt = "2025-04-04"
    ),
    Note(
        id = 5,
        title = "MVVM Architecture",
        content = "Model-View-ViewModel adalah arsitektur yang memisahkan logika bisnis " +
                "dari UI. ViewModel menyimpan dan mengelola UI state.",
        isFavorite = false,
        createdAt = "2025-04-05"
    )
)