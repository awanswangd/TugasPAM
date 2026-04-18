package org.notes.project.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Notes     : BottomNavItem(Screen.NoteList.route,  Icons.Default.Home,    "Notes")
    object Favorites : BottomNavItem(Screen.Favorites.route, Icons.Default.Favorite,"Favorites")
    object Profile   : BottomNavItem(Screen.Profile.route,   Icons.Default.Person,  "Profile")

    companion object {
        val items = listOf(Notes, Favorites, Profile)
    }
}