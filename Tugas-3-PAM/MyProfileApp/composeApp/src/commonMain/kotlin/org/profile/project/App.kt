package org.profile.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import org.profile.project.ui.ProfileScreen
import org.profile.project.viewmodel.ProfileViewModel

@Composable
fun App() {
    val viewModel: ProfileViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    // Pilih color scheme berdasarkan state isDarkMode dari ViewModel
    val colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        ProfileScreen(viewModel = viewModel)
    }
}