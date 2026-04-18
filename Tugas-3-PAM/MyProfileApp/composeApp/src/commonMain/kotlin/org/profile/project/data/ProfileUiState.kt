package org.profile.project.data

/**
 * Data class yang merepresentasikan seluruh UI state halaman profil.
 * Menggunakan immutable data class + copy() untuk setiap update (best practice).
 *
 * @param name          Nama pengguna yang ditampilkan
 * @param title         Jabatan / role pengguna
 * @param bio           Deskripsi singkat pengguna
 * @param email         Alamat email
 * @param phone         Nomor telepon
 * @param location      Lokasi
 * @param avatarInitials Inisial untuk avatar placeholder
 * @param photoUrl      URL foto profil (nullable, fallback ke inisial)
 * @param isDarkMode    Status dark mode
 * @param isEditMode    Apakah sedang dalam mode edit profil
 * @param isSaving      Loading state saat save sedang diproses
 */
data class ProfileUiState(
    val name: String = "M. Hafizurrahman Akbar",
    val title: String = "Mahasiswa Teknik Informatika",
    val bio: String = "Passionate game developer yang gemar membangun game aneh walaupun tidak ada yang memainkannya.",
    val email: String = "mhafizurrahman.123140123@student.itera.ac.id",
    val phone: String = "+62 812-3456-7890",
    val location: String = "Lampung, Indonesia",
    val avatarInitials: String = "HA",
    val photoUrl: String? = null,
    val isDarkMode: Boolean = false,
    val isEditMode: Boolean = false,
    val isSaving: Boolean = false
)
