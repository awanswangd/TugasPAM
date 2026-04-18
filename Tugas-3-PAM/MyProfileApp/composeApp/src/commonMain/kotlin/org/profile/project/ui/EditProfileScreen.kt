package org.profile.project.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector

// ─────────────────────────────────────────────────────────────
// Edit Profile Screen
// ─────────────────────────────────────────────────────────────

/**
 * Halaman form edit profil.
 *
 * Seluruh state (name, bio, title) di-hoist ke ProfileViewModel
 * melalui parameter + callback, sehingga composable ini STATELESS
 * dan mudah di-test.
 *
 * @param name          Nilai teks nama saat ini (dari ViewModel)
 * @param title         Nilai teks title saat ini (dari ViewModel)
 * @param bio           Nilai teks bio saat ini (dari ViewModel)
 * @param onNameChange  Callback saat nama diubah
 * @param onTitleChange Callback saat title diubah
 * @param onBioChange   Callback saat bio diubah
 * @param onSave        Callback saat tombol Save ditekan
 * @param onCancel      Callback saat tombol Back / Cancel ditekan
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    name: String,
    title: String,
    bio: String,
    onNameChange: (String) -> Unit,
    onTitleChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val isSaveEnabled = name.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profil",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                actions = {
                    // Tombol Save di top bar
                    TextButton(
                        onClick = onSave,
                        enabled = isSaveEnabled
                    ) {
                        Text(
                            text = "Simpan",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Judul section
            Text(
                text = "Informasi Dasar",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // ── TextField Nama (State Hoisting) ──────────────
            ProfileTextField(
                label = "Nama Lengkap",
                value = name,
                onValueChange = onNameChange,
                icon = Icons.Default.Person,
                placeholder = "Masukkan nama lengkap",
                imeAction = ImeAction.Next,
                isError = name.isBlank(),
                errorMessage = if (name.isBlank()) "Nama tidak boleh kosong" else null
            )

            // ── TextField Title (State Hoisting) ─────────────
            ProfileTextField(
                label = "Jabatan / Role",
                value = title,
                onValueChange = onTitleChange,
                icon = Icons.Default.Edit,
                placeholder = "Contoh: Mahasiswa Teknik Informatika",
                imeAction = ImeAction.Next
            )

            Divider(modifier = Modifier.padding(vertical = 4.dp))

            Text(
                text = "Tentang Saya",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // ── TextField Bio (State Hoisting, Multi-line) ───
            ProfileTextField(
                label = "Bio",
                value = bio,
                onValueChange = onBioChange,
                icon = Icons.Default.Edit,
                placeholder = "Tulis sedikit tentang dirimu...",
                singleLine = false,
                minLines = 4,
                imeAction = ImeAction.Default
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ── Tombol Save utama di bawah ────────────────────
            Button(
                onClick = onSave,
                enabled = isSaveEnabled,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Simpan Perubahan", fontWeight = FontWeight.Bold)
            }

            // ── Tombol Cancel ─────────────────────────────────
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Batal")
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────
// Reusable Stateless TextField (State Hoisting Pattern)
// ─────────────────────────────────────────────────────────────

/**
 * ProfileTextField — komponen TextField stateless yang reusable.
 *
 * State di-hoist sepenuhnya ke pemanggil melalui [value] dan [onValueChange].
 * Komponen ini tidak menyimpan state apapun secara internal.
 *
 * @param label       Label yang ditampilkan di atas field
 * @param value       Nilai teks saat ini (dari parent/ViewModel)
 * @param onValueChange Callback saat teks berubah → kirim ke parent
 * @param icon        Icon di sisi kiri field
 * @param placeholder Teks placeholder
 * @param singleLine  true = satu baris, false = multi-baris
 * @param minLines    Jumlah baris minimum (aktif jika singleLine = false)
 * @param imeAction   Aksi keyboard (Next, Done, dll)
 * @param isError     Tampilkan state error
 * @param errorMessage Pesan error yang ditampilkan di bawah field
 */
@Composable
fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    placeholder: String = "",
    singleLine: Boolean = true,
    minLines: Int = 1,
    imeAction: ImeAction = ImeAction.Next,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (isError)
                MaterialTheme.colorScheme.error
            else
                MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,          // ← state hoisting
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isError)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primary
                )
            },
            singleLine = singleLine,
            minLines = minLines,
            isError = isError,
            supportingText = if (errorMessage != null) {
                { Text(errorMessage, color = MaterialTheme.colorScheme.error) }
            } else null,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = imeAction
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}
