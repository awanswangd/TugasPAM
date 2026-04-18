package org.profile.project.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.profile.project.data.ProfileUiState

/**
 * ProfileViewModel — menyimpan dan mengelola semua UI state halaman profil.
 *
 * Menggunakan MutableStateFlow secara internal dan mengekspos StateFlow
 * (read-only) ke UI, mengikuti prinsip encapsulation.
 *
 * Survives configuration change (rotasi layar) secara otomatis.
 */
class ProfileViewModel : ViewModel() {

    // Internal mutable state — hanya bisa diubah dari dalam ViewModel
    private val _uiState = MutableStateFlow(ProfileUiState())

    // Public read-only state yang di-observe oleh UI
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    // ─────────────────────────────────────────────────────────
    // Edit Mode
    // ─────────────────────────────────────────────────────────

    /** Masuk ke mode edit — tampilkan form edit profil */
    fun enterEditMode() {
        _uiState.update { it.copy(isEditMode = true) }
    }

    /** Keluar dari mode edit tanpa menyimpan perubahan */
    fun cancelEdit() {
        _uiState.update { it.copy(isEditMode = false) }
    }

    // ─────────────────────────────────────────────────────────
    // Field Update (State Hoisting dari EditProfileScreen)
    // ─────────────────────────────────────────────────────────

    /** Dipanggil setiap kali TextField nama berubah */
    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    /** Dipanggil setiap kali TextField bio berubah */
    fun onBioChange(newBio: String) {
        _uiState.update { it.copy(bio = newBio) }
    }

    /** Dipanggil setiap kali TextField title berubah */
    fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

    // ─────────────────────────────────────────────────────────
    // Save Profile
    // ─────────────────────────────────────────────────────────

    /**
     * Menyimpan perubahan profil dan kembali ke mode tampil.
     *
     * Dalam app nyata, ini akan memanggil Repository untuk menyimpan
     * ke database lokal atau server. Di sini disimulasikan dengan
     * update langsung ke state dan update avatarInitials secara otomatis.
     */
    fun saveProfile() {
        _uiState.update { currentState ->
            val newInitials = currentState.name
                .split(" ")
                .take(2)
                .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                .joinToString("")
                .ifEmpty { "?" }

            currentState.copy(
                isEditMode = false,
                avatarInitials = newInitials
            )
        }
    }

    // ─────────────────────────────────────────────────────────
    // Dark Mode Toggle
    // ─────────────────────────────────────────────────────────

    /**
     * Toggle antara dark mode dan light mode.
     * State disimpan di ViewModel sehingga survive rotasi layar.
     */
    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }
}
