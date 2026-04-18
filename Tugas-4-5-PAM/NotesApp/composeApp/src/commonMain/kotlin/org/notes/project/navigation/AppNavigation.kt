package org.notes.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.*
import org.notes.project.screens.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.NoteList.route
    ) {

        // --- BOTTOM NAV SCREENS ---

        composable(Screen.NoteList.route) {
            NoteListScreen(
                onNoteClick = { noteId ->
                    navController.navigate(Screen.NoteDetail.createRoute(noteId))
                },
                onAddClick = { navController.navigate(Screen.AddNote.route) }
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onNoteClick = { noteId ->
                    navController.navigate(Screen.NoteDetail.createRoute(noteId))
                }
            )
        }

        composable(Screen.Profile.route) { ProfileScreen() }
        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->

            val noteId = backStackEntry.savedStateHandle.get<Int>("noteId") ?: 0

            NoteDetailScreen(
                noteId = noteId,
                onBack  = { navController.popBackStack() },
                onEdit  = { id -> navController.navigate(Screen.EditNote.createRoute(id)) }
            )
        }

        composable(Screen.AddNote.route) {
            AddNoteScreen(
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditNote.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->

            // Gunakan cara yang sama untuk layar edit
            val noteId = backStackEntry.savedStateHandle.get<Int>("noteId") ?: 0

            EditNoteScreen(
                noteId = noteId,
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }

        // --- SETTINGS SCREEN (BONUS) ---

        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}