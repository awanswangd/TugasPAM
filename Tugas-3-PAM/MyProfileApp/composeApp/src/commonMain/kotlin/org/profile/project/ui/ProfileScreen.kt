package org.profile.project.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.profile.project.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    // Collect StateFlow sebagai Compose State
    // UI akan recompose setiap kali uiState berubah
    val uiState by viewModel.uiState.collectAsState()

    // AnimatedContent untuk transisi smooth antara View dan Edit mode
    AnimatedContent(
        targetState = uiState.isEditMode,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "profile_edit_transition"
    ) { isEditing ->
        if (isEditing) {
            // ── Mode Edit ────────────────────────────────────
            EditProfileScreen(
                name = uiState.name,
                title = uiState.title,
                bio = uiState.bio,
                onNameChange = viewModel::onNameChange,   // State hoisting ↑
                onTitleChange = viewModel::onTitleChange, // State hoisting ↑
                onBioChange = viewModel::onBioChange,     // State hoisting ↑
                onSave = viewModel::saveProfile,
                onCancel = viewModel::cancelEdit
            )
        } else {
            // ── Mode Tampil ───────────────────────────────────
            ProfileView(
                uiState = uiState,
                onEditClick = viewModel::enterEditMode,
                onDarkModeToggle = viewModel::toggleDarkMode
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────
// ProfileView — tampilan profil (read-only, stateless)
// ─────────────────────────────────────────────────────────────

/**
 * ProfileView adalah composable STATELESS yang hanya menampilkan data.
 * Semua state berasal dari ProfileUiState dan semua event dikirim
 * ke atas melalui callbacks (State Hoisting pattern).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    uiState: org.profile.project.data.ProfileUiState,
    onEditClick: () -> Unit,
    onDarkModeToggle: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil Saya") },
                actions = {
                    // ── Dark Mode Toggle ──────────────────────
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = if (uiState.isDarkMode)
                                Icons.Default.ShieldMoon    // moon icon placeholder
                            else
                                Icons.Default.ShieldMoon,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Switch(
                            checked = uiState.isDarkMode,
                            onCheckedChange = { onDarkModeToggle() }, // ↑ event naik
                            modifier = Modifier.height(24.dp)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            // ── FAB Edit Profile ──────────────────────────────
            FloatingActionButton(
                onClick = onEditClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profil"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(bottom = 88.dp) // ruang untuk FAB
        ) {
            // 1. Header profil
            ProfileHeader(
                name = uiState.name,
                title = uiState.title,
                avatarInitials = uiState.avatarInitials,
                photoUrl = uiState.photoUrl
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Bio card
            BioCard(bio = uiState.bio)

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Informasi kontak
            ContactInfoCard(
                email = uiState.email,
                phone = uiState.phone,
                location = uiState.location
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Action buttons
            ActionButtons(
                onMessageClick = { /* navigasi ke pesan */ },
                onShareClick = { /* share profil */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Dark Mode card (info visual)
            DarkModeCard(
                isDarkMode = uiState.isDarkMode,
                onToggle = onDarkModeToggle
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────
// DarkModeCard — card khusus untuk toggle dark mode
// ─────────────────────────────────────────────────────────────

/**
 * Card yang menampilkan toggle dark/light mode dengan label dan ikon.
 * State [isDarkMode] dan callback [onToggle] di-hoist dari ViewModel.
 *
 * @param isDarkMode Status dark mode saat ini
 * @param onToggle   Callback saat switch di-toggle
 */
@Composable
fun DarkModeCard(
    isDarkMode: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Icon mode
                Surface(
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = if (isDarkMode) "🌙" else "☀️",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                Column {
                    Text(
                        text = "Tampilan",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = if (isDarkMode) "Dark Mode" else "Light Mode",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Switch — state hoisting via onToggle callback
            Switch(
                checked = isDarkMode,
                onCheckedChange = { onToggle() }  // ↑ event naik ke ViewModel
            )
        }
    }
}
