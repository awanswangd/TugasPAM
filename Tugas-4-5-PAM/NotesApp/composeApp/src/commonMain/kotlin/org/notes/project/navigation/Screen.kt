package org.notes.project.navigation

// Sealed class untuk semua routes — Best Practice: centralized routes
sealed class Screen(val route: String) {

    // Bottom Navigation Screens
    object NoteList : Screen("note_list")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")

    // Detail Screen dengan argument {noteId} — REQUIRED ARGUMENT
    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
        // Contoh: createRoute(3) → "note_detail/3"
    }

    // Edit Screen dengan argument {noteId}
    object EditNote : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Int) = "edit_note/$noteId"
    }

    object AddNote  : Screen("add_note")
    object Settings : Screen("settings")
}